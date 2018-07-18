package com.yukoon.codecenter.controllers;

import com.yukoon.codecenter.entities.Code;
import com.yukoon.codecenter.entities.Reward;
import com.yukoon.codecenter.entities.User;
import com.yukoon.codecenter.services.CodeService;
import com.yukoon.codecenter.services.RewardService;
import com.yukoon.codecenter.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.Map;

@Controller
public class CashController {
    @Autowired
    private CodeService codeService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private UserService userService;

    //前往兑换页面
    @GetMapping("/tocash")
    public String toCash() {
        return "backend/cashing_input";
    }

    //验证兑换码
    @PostMapping("/vaildatecode")
    public String vaildateCode(String code, Map<String,Object> map) {
        Code codeObj = codeService.decode(code);
        Date date = new Date();
        String msg;
        if (null != codeObj && codeObj.getExpiration_date().getTime() >= date.getTime() && codeObj.getStatus()==1) {
            //若兑换码正确且未失效且未被使用
            Reward reward = rewardService.findById(codeObj.getReward_id());
            map.put("reward",reward);
            map.put("code",codeObj);
            msg = "兑换码正确，请仔细确认兑换信息，点击兑换按钮兑换!";
        }else if (null == codeObj){
            //若兑换码不正确
            msg = "兑换码不正确，请确认后重试！";
        }else if (codeObj.getExpiration_date().getTime() <= date.getTime()) {
            //若兑换码已失效
            msg = "兑换码已失效！";
        }else if (codeObj.getStatus() == 2){
            //若兑换码已被使用
            msg = "兑换码已被使用！";
        }else {
            msg = "兑换发生了未知的错误，请联系管理员解决！";
        }
        map.put("msg",msg);
        return "backend/cashing_info";
    }

    //使用兑换码
    @PostMapping("/cashcode")
    public String CashCode(String code, Map<String,Object> map) {
        Subject subject = SecurityUtils.getSubject();
        String msg;
        if (subject.isRemembered() || subject.isAuthenticated()) {
            String username = (String) subject.getPrincipal();
            User user = userService.findByUsername(username);
            if (null != user) {
                Code codeObj = codeService.decode(code);
                Date date = new Date();
                if (null != codeObj && codeObj.getExpiration_date().getTime() >= date.getTime() && codeObj.getStatus()==1) {
                    //若兑换码正确且未失效且未被使用
                    codeObj.setCashing_date(new Date());
                    codeObj.setOperator_id(user.getId());
                    codeService.cash(codeObj);
                    msg = "兑换成功!";
                }else if (null == codeObj){
                    //若兑换码不正确
                    msg = "兑换码不正确，请确认后重试！";
                }else if (codeObj.getExpiration_date().getTime() <= date.getTime()) {
                    //若兑换码已失效
                    msg = "兑换码已失效！";
                }else if (codeObj.getStatus() == 2){
                    //若兑换码已被使用
                    msg = "兑换码已被使用！";
                }else {
                    msg = "兑换发生了未知的错误，请联系管理员解决！";
                }
                map.put("msg",msg);
            }
            return "backend/cashing_info";
        }else {
            return "redirect:/login";
        }
    }
}
