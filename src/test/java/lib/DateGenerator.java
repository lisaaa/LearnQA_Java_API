package lib;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class DateGenerator {

    public static String getRandomEmail(){
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa" + timestamp + "@example.com";
    }

    public static Map<String,String> getUserData(){

        String email = getRandomEmail();
        Map<String, String> userData = new HashMap<>();
        userData.put("email",email);
        userData.put("password","123");
        userData.put("username","learnqa");
        userData.put("firstName","learnqa");
        userData.put("lastName","learnqa");
        return  userData;
        };


    }

