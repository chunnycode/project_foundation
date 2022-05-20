package com.sample.shop.member;

import com.sample.shop.member.dto.LoginDto;
import com.sample.shop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginDto loginDto) throws Exception {
		return ResponseEntity
				.ok()
				.body(memberService.doLogin(loginDto));
	}
}