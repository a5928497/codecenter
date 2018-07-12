package com.yukoon.codecenter.utils;

import com.yukoon.codecenter.entities.Page;

import java.util.ArrayList;
import java.util.List;

public class PageableUtil {

    //进行分页
    public static Page page(Integer pageNo, Integer pageSize, List list) {
        Page page  = new Page();
        //总页数
        int total = 1;
        int start = 0;
        int end = list.size()-1;
        if (list.size()>=pageSize) {
            total = (list.size()%pageSize) ==0?(list.size()/pageSize):((list.size()/pageSize)+1);
            //元素下标起点
            start = (pageNo -1)*pageSize;
            end = pageNo == total?list.size()-1 : start+pageSize-1;
        }
        page.setPageTotal(total);
        page.setPageNo(pageNo).setPageSize(pageSize).setRecordTotal(list.size());
        ArrayList list_new = new ArrayList();
        for (int i = start;i<= end ;i++) {
            list_new.add(list.get(i));
        }
        page.setList(list_new);
        return page;
    }
}
