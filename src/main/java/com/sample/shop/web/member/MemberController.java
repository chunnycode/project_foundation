package com.sample.shop.web.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

	@GetMapping("/login/v")
	public String getLoginPage(Model model){
		return "/member/login";
	}

	@GetMapping("/registration/v")
	public String getRegistrationPage(Model model){
		return "/member/registration";
	}
}
