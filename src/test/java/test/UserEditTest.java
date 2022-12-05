package test;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserEditTest extends BaseTestCase {
    ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test check edit user")
    @DisplayName("Test positive edit user")
    public void testEditJustCreatedTest(){
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



        //EDIT
        String newName = "Change Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName",newName);
        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth,"auth_sid");

        Response responseEditUser =
                apiCoreRequests.makePutRequest(
                        "https://playground.learnqa.ru/api/user/" + userId
                        ,header
                        ,cookie
                        ,editData
                );


        //GET
        Response responseUserData =
                apiCoreRequests.makeGetRequest(
                        "https://playground.learnqa.ru/api/user/" + userId
                        ,header
                        ,cookie
                );

        Assertions.assertJsonByName(responseUserData,"firstName", newName);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Base support for bdd annotations")
    @Description("This test check edit user without authorization")
    @DisplayName("Test negative edit user")
    public void testEditUserWithoutAuthTest(){

        //EDIT
        String newName = "Change Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName",newName);

        Response responseEditUser =
                apiCoreRequests.makePutRequestWithoutTokenAndCookie(
                        "https://playground.learnqa.ru/api/user/2"
                        ,editData
                );

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertResponseTextEquals(responseEditUser,"Auth token not supplied");
    }

    @Test
    @Description("This test check edit another user")
    @DisplayName("Test negative edit user")
    public void testEditAnotherUserTest(){
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

        //EDIT
        String newName = "Change Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("username",newName);
        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth,"auth_sid");

        Response responseEditUser =
                apiCoreRequests.makePutRequest(
                        "https://playground.learnqa.ru/api/user/2"
                        ,header
                        ,cookie
                        ,editData
                );
        Assertions.assertResponseCodeEquals(responseEditUser, 200);

        //GET
        Response responseUserData =
                apiCoreRequests.makeGetRequest(
                        "https://playground.learnqa.ru/api/user/2"
                        ,header
                        ,cookie
                );
        Assertions.assertJsonByName(responseUserData,"username", "Vitaliy");

    }

    @Test
    @Description("This test check edit user with wrong email")
    @DisplayName("Test negative edit user")
    public void testEditUserWithWrongEmailTest(){
        //GENERATE USER
        Map<String, String> userData = DateGenerator.getRegistrationData();

        Response responseCreateAuth =
                apiCoreRequests.makePostRequest(
                        "https://playground.learnqa.ru/api/user/"
                        ,userData);

        String userId = responseCreateAuth.jsonPath().getString("id");
        System.out.println(userId);

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        String expectedEmail = userData.get("email");
        authData.put("email",expectedEmail);
        authData.put("password",userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/login"
                ,authData

        );

        //EDIT
        String newEmail = "newmail.ru";
        Map<String, String> editData = new HashMap<>();
        editData.put("email",newEmail);
        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth,"auth_sid");

        Response responseEditUser =
                apiCoreRequests.makePutRequest(
                        "https://playground.learnqa.ru/api/user/" + userId
                        ,header
                        ,cookie
                        ,editData
                );
        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertResponseTextEquals(responseEditUser,"Invalid email format");

    }

    @Test
    @Description("This test check edit user with short username")
    @DisplayName("Test negative edit user")
    public void testEditUserWithShortNameTest(){
        //GENERATE USER
        Map<String, String> userData = DateGenerator.getRegistrationData();

        Response responseCreateAuth =
                apiCoreRequests.makePostRequest(
                        "https://playground.learnqa.ru/api/user/"
                        ,userData);

        String userId = responseCreateAuth.jsonPath().getString("id");
        System.out.println(userId);

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        String expectedUsername = userData.get("username");
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/login"
                ,authData

        );
        responseGetAuth.prettyPrint();

        //EDIT
        String newUserName = "t";
        Map<String, String> editData = new HashMap<>();
        editData.put("username",newUserName);
        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth,"auth_sid");

        Response responseEditUser =
                apiCoreRequests.makePutRequest(
                        "https://playground.learnqa.ru/api/user/" + userId
                        ,header
                        ,cookie
                        ,editData
                );
        System.out.println(responseEditUser.prettyPrint());

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertResponseTextContains(responseEditUser,"Too short value for field username");

    }

}
