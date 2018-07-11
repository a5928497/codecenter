package com.yukoon.codecenter.controllers;

import com.yukoon.codecenter.entities.User;
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

@Controller
public class LoginController {
	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String toLogin() {
		return "backend/login";
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
			return "redirect:/login";
		}
		return "redirect:/login";
	}
}
