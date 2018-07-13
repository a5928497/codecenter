package com.yukoon.codecenter.controllers;

import com.yukoon.codecenter.entities.Page;
import com.yukoon.codecenter.services.RecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RecordController {
    private static final Integer PAGE_SIZE = 10;
    @Autowired
    private RecordService recordService;

    @RequiresRoles("admin")
    @RequiresPermissions("query")
    @GetMapping("/getallrecords")
    public String findAll(@RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo,
                          Map<String,Object> map) {
        if (pageNo <1) {
            pageNo =1;
        }
        Page page = recordService.findALL(pageNo,PAGE_SIZE);
        map.put("all","/getallrecords");
        map.put("page",page);
        return "backend/dashboard";
    }
}
