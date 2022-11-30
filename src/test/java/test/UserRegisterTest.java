package test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.Test;


import java.util.Map;

@Epic("Register cases")
@Feature("Registration")
public class UserRegisterTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("This test check register with existing email")
    @DisplayName("Test negative register user")
    public void testCreateUserWithExistingEmail(){
        Map<String, String> userData = DateGenerator.getUserData();
        userData.replace("email","vinkotov@example.com");
       Response responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/"
                ,userData
        );
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth,"Users with email '" + userData.get("email") + "' already exists");
    }
    @Test
    @Description("This test check register with incorrect email")
    @DisplayName("Test negative register user")
    public void testCreateUserWithIncorrectEmail(){

        Map<String, String> userData = DateGenerator.getUserData();
        userData.replace("email","vinkotovexample.com");
        Response responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/"
                ,userData
        );
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth,"Invalid email format");
    }
    @Test
    @Description("This test check register with short user name")
    @DisplayName("Test negative register user")
    public void testCreateUserWithShortName(){
        Map<String, String> userData = DateGenerator.getUserData();
        userData.replace("username","t");
        Response responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/"
                ,userData
        );
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth,"The value of 'username' field is too short");
    }
    @Test
    @Description("This test check register with long user name")
    @DisplayName("Test negative register user")
    public void testCreateUserWithLongName(){
        String longUserName = "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.,\n" +
                "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.,Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.,Да, обязательно. Всем самокатов! И Москве, и Московской области";
        Map<String, String> userData = DateGenerator.getUserData();
        userData.replace("username",longUserName);
        Response responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/"
                ,userData
        );
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth,"The value of 'username' field is too long");
    }


    @ParameterizedTest
    @ValueSource(strings = {"firstName", "lastName",  "password",  "email",  "username"})
    @Description("This test check register user without some field")
    @DisplayName("Test negative register user")
    public void testCreateUserWithoutFields(String key){
        Map<String, String> userData = DateGenerator.getUserData();
        userData.remove(key);
        System.out.println(userData);
        Response responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/"
                ,userData
        );
            Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
            Assertions.assertResponseTextEquals(responseCreateAuth, "The following required params are missed: " + key );

    }

}
