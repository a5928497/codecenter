package com.yukoon.codecenter.utils;

import com.yukoon.codecenter.entities.Code;

import java.util.ArrayList;
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

    public static void main(String[] args) {

    }
}
