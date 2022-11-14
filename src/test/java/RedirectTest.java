import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;
import io.restassured.http.Headers;

public class RedirectTest {

    @Test
    public void testHelloWorld(){
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        response.prettyPrint();

        String locationHeaders = response.getHeader("Location");
        System.out.println(locationHeaders);


    }
}
