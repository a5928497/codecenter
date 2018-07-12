package com.yukoon.codecenter.controllers;

import com.yukoon.codecenter.entities.Page;
import com.yukoon.codecenter.services.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class UserController {
    private static final Integer PAGE_SIZE = 10;
    @Autowired
    private UserService userService;

    //后台查询所有用户
    @RequiresRoles("admin")
    @RequiresPermissions("query")
    @GetMapping("/users")
    public String findAll(@RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo,
                          Map<String,Object> map) {
        if (pageNo<1) {
            pageNo = 1;
        }
        Page page = userService.findAll(pageNo,PAGE_SIZE);
        map.put("page",page);
        return "backend/all_user_list";
    }

    //后台按姓名搜索用户
    @RequiresRoles("admin")
    @RequiresPermissions("query")
    @PostMapping("/searchuser")
    public String searchByRealName(@RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo,
                          Map<String,Object> map,String realName) {
        if (pageNo<1) {
            pageNo = 1;
        }
        Page page = userService.seearchByRealName(pageNo,PAGE_SIZE,realName);
        map.put("page",page);
        return "backend/all_user_list";
    }
}
