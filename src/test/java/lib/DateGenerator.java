package lib;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class DateGenerator {

    public static String getRandomEmail(){
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa" + timestamp + "@example.com";
    }

    public static Map<String,String> getRegistrationData(){

        String email = getRandomEmail();
        Map<String, String> userData = new HashMap<>();
        userData.put("email",email);
        userData.put("password","123");
        userData.put("username","learnqa");
        userData.put("firstName","learnqa");
        userData.put("lastName","learnqa");
        return  userData;
        };

    public static Map<String,String> getRegistrationData(Map<String, String> nonDefaultValues) {
        Map<String, String> defaultValues = DateGenerator.getRegistrationData();

        Map<String, String> userData = new HashMap<>();
        String[] keys = {"email","password","username","firstname","lastname"};
        for (String key : keys) {
            if (nonDefaultValues.containsKey(key)){
                userData.put(key,nonDefaultValues.get(key));
            } else {
                userData.put(key,defaultValues.get(key));
            }
        }

        return  userData;
    };


    }

