package com.sample.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberPageController {

	@GetMapping("/login")
	public String getLoginPage(Model model){
		model.addAttribute("test", "로그인을 해");
		return "/member/login";
	}

	@GetMapping("/registration")
	public String getRegistrationPage(Model model){
		model.addAttribute("test", "회원가입을 해");
		return "/member/registration";
	}
}
