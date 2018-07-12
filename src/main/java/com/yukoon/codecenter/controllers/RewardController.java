package com.yukoon.codecenter.controllers;

import com.yukoon.codecenter.entities.Page;
import com.yukoon.codecenter.entities.Reward;
import com.yukoon.codecenter.services.RewardService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RewardController {
    private final static Integer PAGE_SIZE = 10;
    @Autowired
    private RewardService rewardService;

    //后台查询所有礼品
    @RequiresRoles("admin")
    @RequiresPermissions("query")
    @GetMapping("/rewards")
    public String findAll(@RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo,
                          Map<String,Object> map) {
        if (pageNo<1) {
            pageNo = 1;
        }
        Page page = rewardService.findAll(pageNo,PAGE_SIZE);
        map.put("page",page);
        return "backend/all_reward_list";
    }

    //后台前往添加礼品
    @RequiresRoles("admin")
    @RequiresPermissions("add")
    @GetMapping("/reward")
    public String toAdd() {
        return "backend/reward_input";
    }

    //后台添加礼品
    @RequiresRoles("admin")
    @RequiresPermissions("add")
    @PostMapping("/reward")
    public String Add(Reward reward) {
        rewardService.addReward(reward);
        return "redirect:/rewards";
    }
}
