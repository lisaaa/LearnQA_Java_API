package test;

import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserDeleteTest extends BaseTestCase {


    ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("test-1")
    @Story("Base support for bdd annotations")
    @Description("This test delete user by id")
    @DisplayName("Test positive delete user test")
    public void testDeleteNewUserTest(){
        //GENERATE USER
        Map<String, String> userData = DateGenerator.getRegistrationData();

        Response responseCreateAuth =
                apiCoreRequests.makePostRequest(
                        "https://playground.learnqa.ru/api/user/"
                        ,userData);

        String userId = responseCreateAuth.jsonPath().getString("id");

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/login"
                ,authData

        );

        //DELETE
        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth,"auth_sid");

        Response responseDeleteUser =
                apiCoreRequests.makeDeleteRequest(
                        "https://playground.learnqa.ru/api/user/" + userId
                        ,header
                        ,cookie
                );
        Assertions.assertResponseCodeEquals(responseDeleteUser, 200);

        //GET
        Response responseUserData =
                apiCoreRequests.makeGetRequest(
                        "https://playground.learnqa.ru/api/user/"  + userId
                        ,header
                        ,cookie
                );

        Assertions.assertResponseTextContains(responseUserData,"User not found");

    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @TmsLink("test-2")
    @Description("This test delete exist user by id")
    @DisplayName("Test negative delete user test")
    public void testDeleteExistUserTest(){
        int userId = 2;

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email","vinkotov@example.com");
        authData.put("password","1234");

        Response responseGetAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/login"
                ,authData
        );

        //DELETE
        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth,"auth_sid");

        Response responseDeleteUser =
                apiCoreRequests.makeDeleteRequest(
                        "https://playground.learnqa.ru/api/user/" + userId
                        ,header
                        ,cookie
                );
        Assertions.assertResponseCodeEquals(responseDeleteUser, 400);

        //GET
        Response responseUserData =
                apiCoreRequests.makeGetRequest(
                        "https://playground.learnqa.ru/api/user/"  + userId
                        ,header
                        ,cookie
                );

        String[] expectedFields = {"id","username","firstName","lastName","email"};
        Assertions.assertJsonHasFields(responseUserData,expectedFields);

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("test-3")
    @Description("This test delete another user by id")
    @DisplayName("Test negative delete user test")
    public void testDeleteAnotherUserTest(){
        //GENERATE NEW USER
        Map<String, String> userData = DateGenerator.getRegistrationData();

        Response responseCreateAuth =
                apiCoreRequests.makePostRequest(
                        "https://playground.learnqa.ru/api/user/"
                        ,userData);
        String userIdNew = responseCreateAuth.jsonPath().getString("id");

        //GENERATE USER FOR DELETE
        Map<String, String> userDataForDel = DateGenerator.getRegistrationData();

        Response responseCreateAuthForDel =
                apiCoreRequests.makePostRequest(
                        "https://playground.learnqa.ru/api/user/"
                        ,userDataForDel);
        Assertions.assertResponseCodeEquals(responseCreateAuthForDel, 200);

        String userIdForDel = responseCreateAuthForDel.jsonPath().getString("id");

        //LOGIN NEW USER
        Map<String, String> authData = new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/login"
                ,authData

        );
        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth,"auth_sid");

        //DELETE
        Response responseDeleteUser =
                apiCoreRequests.makeDeleteRequest(
                        "https://playground.learnqa.ru/api/user/" + userIdForDel
                        ,header
                        ,cookie
                );
        Assertions.assertResponseCodeEquals(responseDeleteUser, 200);

        //GET NEW USER
        Response responseUserData =
                apiCoreRequests.makeGetRequest(
                        "https://playground.learnqa.ru/api/user/"  + userIdForDel
                        ,header
                        ,cookie
                );

        Assertions.assertJsonByName(responseUserData,"username","learnqa");

        //GET USER FOR DELETE
        Response responseUserData2 =
                apiCoreRequests.makeGetRequest(
                        "https://playground.learnqa.ru/api/user/"  + userIdNew
                        ,header
                        ,cookie
                );

        Assertions.assertResponseTextContains(responseUserData2,"User not found");

    }

}
