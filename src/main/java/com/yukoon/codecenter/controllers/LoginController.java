package com.yukoon.codecenter.controllers;

import com.yukoon.codecenter.entities.Page;
import com.yukoon.codecenter.entities.Record;
import com.yukoon.codecenter.entities.Reward;
import com.yukoon.codecenter.entities.User;
import com.yukoon.codecenter.services.RecordService;
import com.yukoon.codecenter.services.RewardService;
import com.yukoon.codecenter.services.UserService;
import com.yukoon.codecenter.utils.EncodeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
	private final static Integer PAGE_SIZE =10;
	@Autowired
	private UserService userService;
	@Autowired
	private RecordService recordService;
	@Autowired
	private RewardService rewardService;

	@GetMapping("/login")
	public String toLogin() {
		return "backend/login";
	}

	@GetMapping("/dashboard")
	public String toDashboard(@RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo,
							  Map<String,Object> map) {
		if (pageNo <1) {
			pageNo =1;
		}
		Subject currentUser = SecurityUtils.getSubject();
		String username = (String) currentUser.getPrincipal();
		if (null == username) {
			return "redirect:/login";
		}else {
			User user = userService.findByUsername(username);
			Page page = recordService.findByUserId(pageNo,PAGE_SIZE,user.getId());
			map.put("page",page);
		}
		return "backend/dashboard";
	}

	@PostMapping("/login")
	public String login(User user) {
		//获得subject
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			//把用户名密码封装为Token对象
			String username = user.getUsername();
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, EncodeUtil.encodePassword(user.getPassword(), username));
			//设置token的rememberme
			usernamePasswordToken.setRememberMe(true);
			try {
				//执行登录
				currentUser.login(usernamePasswordToken);
			} catch (AuthenticationException ae) {
				System.out.println("登陆失败:" + ae.toString());
				return "redirect:/login";
			}
			System.out.println("登陆成功！");
			return "redirect:/dashboard";
		}else {
			return "redirect:/login";
		}
	}
}
