package com.sample.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class WelcomePageController {

	@GetMapping("/welcome")
	public String getWelcomePage(Model model){
		model.addAttribute("test", "하이하이");
		return "/member/welcome";
	}

}
