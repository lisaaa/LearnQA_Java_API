import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;


public class ResearchPasswordTest {
    @Test
    public void testPassword(){
        Map<String,String> params = new HashMap<>();
       params.put("password1","123456");
        params.put("password2","12345679");
        params.put("password3","qwerty");
        params.put("password4","12345678");
        params.put("password5","111111");
        params.put("password6","1234567890");
        params.put("password7","1234567");
        params.put("password8","password");
        params.put("password9","123123");
        params.put("password10","987654321");
        params.put("password11","qwertyuiop");
        params.put("password12","mynoob");
        params.put("password13","123321");
        params.put("password14","666666");
        params.put("password15","18atcskd2w");
        params.put("password16","7777777");
        params.put("password17","1q2w3e4r");
        params.put("password18","654321");
        params.put("password19","555555");
        params.put("password20","3rjs1la7qe");
        params.put("password21","google");
        params.put("password22","1q2w3e4r5t");
        params.put("password23","123qwe");
        params.put("password24","zxcvbnm");
        params.put("password25","1q2w3e");
        params.put("password26","12345");
        params.put("password27","iloveyou");
        params.put("password28","123123");
        params.put("password29","abc123");
        params.put("password30","qwerty123");
        params.put("password31","qwerty123");
        params.put("password32","admin");
        params.put("password33","lovely");
        params.put("password34","welcome");
        params.put("password35","888888");
        params.put("password36","princess");
        params.put("password37","dragon");
        params.put("password38","password1");
        params.put("password39","sunshine");
        params.put("password40","football");
        params.put("password41","passw0rd");
        params.put("password42","master");
        params.put("password43","hello");
        params.put("password44","freedom");
        params.put("password45","whatever");
        params.put("password46","qazwsx");
        params.put("password47","trustno1");
        params.put("password48","letmein");
        params.put("password49","monkey");
        params.put("password50","login");
        params.put("password51","starwars");
        params.put("password52","1234");
        params.put("password53","solo");
        params.put("password54","121212");
        params.put("password55","flower");
        params.put("password56","hottie");
        params.put("password57","loveme");
        params.put("password58","zaq1zaq1");
        params.put("password59","baseball");
        params.put("password60","mustang");



        for (String pas : params.values()) {
            Response responseGetSecretPassword = RestAssured
                    .given()
                    .queryParams("login","super_admin")
                    .queryParams("password",pas)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    ;

            String responseCookie = responseGetSecretPassword.getCookie("auth_cookie");

            Response responseCheckAuthCookie = RestAssured
                    .given()
                    .cookie("auth_cookie",responseCookie)
                    .when()
                    .get("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    ;
//welcome
            Boolean result = responseCheckAuthCookie.asPrettyString().contains("You are NOT authorized");

         if (result == false){
             System.out.println("Правильный пароль: " + pas);
             System.out.println(responseCheckAuthCookie.prettyPrint());

         };

        }
    }
}
