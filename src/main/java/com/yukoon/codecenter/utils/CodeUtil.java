package com.yukoon.codecenter.utils;

import com.yukoon.codecenter.entities.Code;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeUtil {
    //0-61
    private static final String[] chars = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e",
            "f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B",
            "C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public static List<Code> getKey(int quantity,String lastCode) {
        Integer startPosition = getStartPosition(lastCode);
        List<Code> list = new ArrayList<>();
        StringBuffer str = new StringBuffer();
        return list;
    }

    public static Integer getStartPosition(String str) {
        String s = String.valueOf(str.charAt(str.length()-1));
        Integer result = null;
        for (int i = 0;i<62;i++) {
            if (s.equals(chars[i])) {
                result = i+1;
                if (result > 61) {
                    result = 0;
                }
                return result;
            }
        }
        return result;
    }
    public static void main(String[] args) {
        String start = "000400000000000Y";
        System.out.println(getStartPosition(start));
    }
}
