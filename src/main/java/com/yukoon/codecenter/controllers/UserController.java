package com.yukoon.codecenter.controllers;

import com.yukoon.codecenter.entities.Page;
import com.yukoon.codecenter.entities.User;
import com.yukoon.codecenter.services.RoleService;
import com.yukoon.codecenter.services.UserService;
import com.yukoon.codecenter.utils.EncodeUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class UserController {
    private static final Integer PAGE_SIZE = 10;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

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

    //后台前往添加用户
    @RequiresRoles("admin")
    @RequiresPermissions("add")
    @GetMapping("/toadduser")
    public String toAdd(Map<String,Object> map,String errMsg) {
        if (errMsg != null) {
            map.put("errMsg",errMsg);
        }
        map.put("roles",roleService.findAll());
        return "backend/user_input";
    }

    //后台添加用户
    @RequiresRoles("admin")
    @RequiresPermissions("add")
    @PostMapping("/user")
    public String Add(User user, RedirectAttributes attributes) {
        if (userService.isUnique(user.getUsername())) {
            user.setPassword(EncodeUtil.encodePassword(user.getPassword(),user.getUsername()));
            //添加管理员
            if (user.getRole_id() == 1) {
                userService.addAdmin(user);
            }else {
                //添加经办员
                userService.addUser(user);
            }
            return "redirect:/users";
        }else {
            attributes.addFlashAttribute("errMsg","该登录账号已存在！");
            return "redirect:/toadduser";
        }
    }

    //后台前往编辑用户
    @RequiresRoles("admin")
    @RequiresPermissions("edit")
    @GetMapping("/user/{id}")
    public String toEdit(@PathVariable("id")Integer id, Map<String,Object> map, String errMsg) {
        map.put("user",userService.findByid(id));
        map.put("roles",roleService.findAll());
        return "backend/user_input";
    }
}
