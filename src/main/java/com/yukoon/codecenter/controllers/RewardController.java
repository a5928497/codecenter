package com.yukoon.codecenter.controllers;

import com.yukoon.codecenter.entities.Page;
import com.yukoon.codecenter.entities.Reward;
import com.yukoon.codecenter.services.RewardService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class RewardController {
    private final static Integer PAGE_SIZE = 10;
    @Autowired
    private RewardService rewardService;

    @ModelAttribute
    public void getReward(@RequestParam(value = "id",required = false)Integer id,Map<String,Object> map) {
        //若为修改，则id不为空
        if (id != null) {
            map.put("reward",rewardService.findById(id));
        }
    }

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

    //后台按礼品名搜索礼品
    @RequiresRoles("admin")
    @RequiresPermissions("query")
    @PostMapping("/searchreward")
    public String search(@RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo,
                          Map<String,Object> map,String reward_name) {
        if (pageNo<1) {
            pageNo = 1;
        }
        if (reward_name.equals("")) {
            return "redirect:/rewards";
        }
        Page page = new Page().setList(rewardService.search(reward_name));
        map.put("page",page);
        map.put("search","search");
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

    //后台前往编辑礼品
    @RequiresRoles("admin")
    @RequiresPermissions("edit")
    @GetMapping("/reward/{id}")
    public String toEdit(@PathVariable("id")Integer id,Map<String,Object>map) {
        map.put("reward",rewardService.findById(id));
        return "backend/reward_input";
    }

    //后台编辑礼品
    @RequiresRoles("admin")
    @RequiresPermissions("edit")
    @PutMapping("/reward")
    public String edit(Reward reward) {
        rewardService.update(reward);
        return "redirect:/rewards";
    }

    //后台上架礼品
    @RequiresRoles("admin")
    @RequiresPermissions("edit")
    @GetMapping("/alive/{id}")
    public String alive(@PathVariable("id")Integer id) {
        rewardService.alive(id);
        return "redirect:/rewards";
    }

    //后台下架礼品
    @RequiresRoles("admin")
    @RequiresPermissions("edit")
    @GetMapping("/block/{id}")
    public String block(@PathVariable("id")Integer id) {
        rewardService.block(id);
        return "redirect:/rewards";
    }

    //后台删除礼品
    @RequiresRoles("admin")
    @RequiresPermissions("delete")
    @DeleteMapping("/reward/{id}")
    public String del(@PathVariable("id")Integer id) {
        rewardService.delete(id);
        return "redirect:/rewards";
    }
}
