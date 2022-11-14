import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class RedirectTest {

    @Test
    public void testRedirect(){
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

    @Test
    public void testLongRedirect(){

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        String redirectURL =  response.getHeader("Location");;
        int statusCode = response.getStatusCode();

        while (statusCode == 301 ) {
            Response resp = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(redirectURL)
                    .andReturn();

            redirectURL = resp.getHeader("Location");
            statusCode = resp.getStatusCode();

        };

        System.out.println(statusCode);
        System.out.println(redirectURL);

    }
}
