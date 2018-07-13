package com.yukoon.codecenter.utils;

import com.yukoon.codecenter.entities.Code;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeUtil {

    private final static String salt = "acPAgHpeEIfFCb8C";

    public static String encodeCode(long startpos) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        List<Integer> list =new ArrayList<>();
        String start = String.valueOf(startpos);
        char[] temp = salt.toCharArray();
        int size = start.length();
        for (int i = 0;i<size;i++) {
            list.add(Integer.valueOf(start.substring(i,i+1)));
        }
        int fill_size = 14-size;
        for (int i = 0;i<fill_size ;i++) {
            list.add(random.nextInt(10));
        }
        if (size<10) {
            list.add(0);
            list.add(size);
        }else {
            list.add(1);
            list.add(size-10);
        }
        for (int i = 0;i<16 ;i++) {
            temp[i] = (char)(temp[i]+list.get(i));
        }
        return String.valueOf(temp);
    }

    public static Long decodeCode(String code) {
        char[] temp1 = code.substring(14).toCharArray();
        char[] temp2 = salt.substring(14).toCharArray();
        int size = 0;
        if (temp1[0]-temp2[0]!=1) {
            size = temp1[1]-temp2[1];
        }else {
            size = temp1[1]-temp2[1] +10;
        }
        char[] code_temp = code.substring(0,size).toCharArray();
        char[] salt_temp = salt.substring(0,size).toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i<size  ;i++) {
            sb.append(code_temp[i]-salt_temp[i]);
        }
        return Long.valueOf(sb.toString());
    }

    public static void main(String[] args) {
        System.out.println(encodeCode(54875105876L));
        System.out.println(decodeCode("fgXHlIpjMPlMGd9D"));
    }
}
