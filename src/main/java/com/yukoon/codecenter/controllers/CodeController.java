package com.yukoon.codecenter.controllers;

import com.yukoon.codecenter.services.RewardService;
import com.yukoon.codecenter.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.Map;

@Controller
public class CodeController {
	@Autowired
	private RewardService rewardService;
	@Autowired
	private UserService userService;

	//后台前往兑换券申领页面
	@GetMapping("/code")
	public String toGetCodes(Map<String,Object> map) {
		Subject currentUser  = SecurityUtils.getSubject();
		String user = currentUser.getPrincipal().toString();
		if (user != null) {
			map.put("rewards",rewardService.findAllAlive());
			return "backend/get_codes_input";
		}else {
			return "redirect:/logout";
		}

	}
}
