package other;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetCookiesTest {

    @Test
    public void checkCookieResponse(){
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        Map<String, String> cookies = new HashMap<>();
        cookies = response.getCookies();
        System.out.println(cookies);
        assertTrue("Response has 'HomeWork' cookie", cookies.containsKey("HomeWork"));
        assertEquals("hw_value", cookies.get("HomeWork"));
    }
}
