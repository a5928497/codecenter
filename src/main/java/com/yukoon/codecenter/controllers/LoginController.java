package com.yukoon.codecenter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String toLogin() {
		return "backend/login";
	}
}
