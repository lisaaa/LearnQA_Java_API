package test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Test;


import java.util.HashMap;
import java.util.Map;

@Epic("Register cases")
@Feature("Registration")
@RunWith(Parameterized.class)
public class UserRegisterTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    private final String email;
    private final String password;
    private final String username;
    private final String firstName;
    private final String lastName;

    @Parameterized.Parameters (name = "email = {0},password = {1},username = {2},  firstName = {3},lastName = {4},")
    public static Object[][] getUserData() {
        return new Object[][]{
                {"","123","test@name","first","last"},
                {"test@mail.ru","","testName","testFirstName","testLastName"},
                {"test@mail.ru","123","","testFirstName","testLastName"},
                {"test@mail.ru","123","testName","","testLastName"},
                {"test@mail.ru","123","testName","testFirstName",""},

        };
    }
    public UserRegisterTest(String email, String password,String username,String firstName,String lastName){
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    @Test
    @Description("This test check register with existing email")
    @DisplayName("Test negative register user")
    public void testCreateUserWithExistingEmail(){
        String email = "vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email",email);
        userData.put("password","123");
        userData.put("username","learnqa");
        userData.put("firstName","learnqa");
        userData.put("lastName","learnqa");

        Response responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/"
                ,userData
        );

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth,"Users with email '" + email + "' already exists");
    }
    @Test
    @Description("This test check register with incorrect email")
    @DisplayName("Test negative register user")
    public void testCreateUserWithIncorrectEmail(){

        Map<String, String> userData = new HashMap<>();
        userData.put("email", "testmail.ru");
        userData.put("password","123");
        userData.put("username","learnqa");
        userData.put("firstName","learnqa");
        userData.put("lastName","learnqa");

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
        String email = "vinkotov@example.com";
        String username = "t";

        Map<String, String> userData = new HashMap<>();
        userData.put("email",email);
        userData.put("password","123");
        userData.put("username",username);
        userData.put("firstName","learnqa");
        userData.put("lastName","learnqa");

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
        String email = "vinkotov@example.com";
        String username = "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.,\n" +
                "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.,Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.,Да, обязательно. Всем самокатов! И Москве, и Московской области";

        Map<String, String> userData = new HashMap<>();
        userData.put("email",email);
        userData.put("password","123");
        userData.put("username",username);
        userData.put("firstName","learnqa");
        userData.put("lastName","learnqa");

        Response responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/"
                ,userData
        );

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth,"The value of 'username' field is too long");
    }

    @Test
    @Description("This test check register user without some field")
    @DisplayName("Test negative register user")
    public void testCreateUserWithoutFields(){
        Map<String, String> userData = new HashMap<>();
        userData.put("email",this.email);
        userData.put("password",this.password);
        userData.put("username",this.username);
        userData.put("firstName",this.firstName);
        userData.put("lastName",this.lastName);

        Response responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/"
                ,userData
        );

        if (userData.get("email").length()==0) {
            Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
            Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'email' field is too short");
        } else if (userData.get("password").length()==0){
            Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
            Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'password' field is too short");
        } else if (userData.get("username").length()==0){
            Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
            Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'username' field is too short");
        }
        else if (userData.get("firstName").length()==0){
            Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
            Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too short");
        }
        else if (userData.get("lastName").length()==0){
            Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
            Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'lastName' field is too short");
        }
    }

}
