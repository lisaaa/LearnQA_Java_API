import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class UserAgentTest {


    private final int number;
    private final String header;
    private final String platform;
    private final String browser;
    private final String device;

    @Parameterized.Parameters (name = "number = {0},platform = {1},browser = {2},device = {3},  header = {4},")
    public static Object[][] getHeaderUserAgent() {
        return new Object[][]{
                {0,"Mobile", "No", "Android","'Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30'"},
                {1,"Mobile", "Chrome", "iOS","'Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1'"},
                {2,"Googlebot", "Unknown", "Unknown","'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)'"},
                {3,"Web", "Chrome", "No","'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0'"},
                {4,"Mobile", "No", "iPhone","'Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1'"},

        };
    }
    public UserAgentTest(int number, String platform,String browser,String device,String header){
        this.number = number;
        this.header = header;
        this.platform = platform;
        this.browser = browser;
        this.device = device;

    }
    @Test
    public void checkUserAgent(){


            Response resonse = RestAssured
                    .given()
                    .header("user-agent",header)
                    .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                    .andReturn();

            String actualPlatform = resonse.jsonPath().get("platform");
            String actualBrowser = resonse.jsonPath().get("browser");
            String actualDevice = resonse.jsonPath().get("device");

            System.out.println(actualPlatform + " " + actualBrowser + " " + actualDevice);
            assertEquals(platform,actualPlatform,"JSON value is not equal to expected value");
            assertEquals(browser,actualBrowser,"JSON value is not equal to expected value");
            assertEquals(device,actualDevice,"JSON value is not equal to expected value");

    }
}
