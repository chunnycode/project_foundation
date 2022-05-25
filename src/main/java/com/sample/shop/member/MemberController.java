package com.sample.shop.member;

import com.sample.shop.login.dto.LoginDto;
import com.sample.shop.member.dto.MemberJoinDto;
import com.sample.shop.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/join")
	public ResponseEntity join(@RequestBody MemberJoinDto memberJoinDto) throws Exception {
		return null;
		/*return ResponseEntity
				.ok()
				.body(); // memberService.join(joinDto)*/
	}



}