import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class TokenTest {
    @Test
    public void testGetToken() throws InterruptedException {
        JsonPath responseCreateJob = RestAssured
                .given()
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn()
                .jsonPath();
        String token = responseCreateJob.get("token");
        System.out.println("responseCreateJob: " + responseCreateJob.prettyPrint() );

        Response responseBeforeReadyTask = RestAssured
                .given()
                .queryParam("token",token)
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();
        int statusCode = responseBeforeReadyTask.getStatusCode();
        Assert.assertEquals(statusCode,200);
        System.out.println("responseBeforeReadyTask: " + responseBeforeReadyTask.prettyPrint());

        Thread.currentThread().sleep(18000);

        JsonPath responseReadyTask = RestAssured
                .given()
                .queryParam("token",token)
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn()
                .jsonPath();
        Assert.assertEquals("Job is ready",responseReadyTask.get("status"));
        String result = responseReadyTask.get("result");
        Assert.assertNotNull(result);
    }
}
