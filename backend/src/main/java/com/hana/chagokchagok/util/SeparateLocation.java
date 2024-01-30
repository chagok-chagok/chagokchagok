package com.hana.chagokchagok.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeparateLocation {
    /**
     * FullName을 구역과 번호로 나눠주는 메소드
     * @param input
     * @return [0]은 구역, [1]은 번호
     */
    public static String[] separateLocationInput(String input){
        String[] answer = new String[2];

        String regex = "([A-Za-z]+)(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // 매칭된 경우에만 그룹을 가져옴
        if (matcher.matches()) {
            answer[0] = matcher.group(1);
            answer[1] = matcher.group(2);
        }
        return answer;
    }
}
