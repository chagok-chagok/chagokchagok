package com.hana.chagokchagok.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateCarNum {
    public static boolean validateCarNum(String carNum){
        String regax = "^[0-9]{2}[가-힣][0-9]{4}$";
        Pattern pattern = Pattern.compile(regax);
        Matcher matcher = pattern.matcher(carNum);
        if(matcher.matches()) {
            return true;
        } else{
            return false;
        }
    }
}
