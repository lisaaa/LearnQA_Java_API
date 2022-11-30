package other;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetHeadersTest {

    @Test
    public void checkHeadersResponse(){
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        Headers headers = response.getHeaders();
        System.out.println(headers);
        assertTrue("Response has header ", headers.hasHeaderWithName("x-secret-homework-header"));
        assertEquals("Some secret value", headers.getValue("x-secret-homework-header"));
    }
}