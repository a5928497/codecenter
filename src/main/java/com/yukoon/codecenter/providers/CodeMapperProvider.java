package com.yukoon.codecenter.providers;

import com.yukoon.codecenter.entities.Code;

import java.text.MessageFormat;
import java.util.*;

public class CodeMapperProvider {

    public String insertAll(Map map) {
        List<Code> codes = (List<Code>) map.get("list");
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO codes (code,status,reward_id,record_id,expiration_date) VALUES");
        MessageFormat mf = new MessageFormat("#'{'list[{0}].code'}',#'{'list[{0}].status'}',#'{'list[{0}].reward_id'}'," +
                "#'{'list[{0}].record_id'}',#'{'list[{0}].expiration_date'}'");
        for (int i = 0;i< codes.size();i++) {
            sb.append("(");
            //这里如果直接用数字，超过1000会格式化变成1,000，但是String类型就没问题
            sb.append(mf.format(new Object[]{String.valueOf(i)}));
            sb.append(")");

            if (i < codes.size() - 1) {
                sb.append(",");
            }
        }
        return  sb.toString();
    }

    public static void main(String[] args) {
        Code code1 = new Code().setCode("123123").setStatus(1).setReward_id(1).setRecord_id(1).setExpiration_date(new Date());
        Code code2 = new Code().setCode("sdfdsfd").setStatus(1).setReward_id(1).setRecord_id(1).setExpiration_date(new Date());
        List<Code> list = new ArrayList<>();
        list.add(code1);
        list.add(code2);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        System.out.println(new CodeMapperProvider().insertAll(map));
    }
}
