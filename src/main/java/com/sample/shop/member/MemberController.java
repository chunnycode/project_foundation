package com.sample.shop.member;

import com.sample.shop.dto.MemberRequest;
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
	public ResponseEntity login(@RequestBody MemberRequest memberRequest) throws Exception {
		return ResponseEntity
				.ok()
				.body(memberService.doLogin(memberRequest));
	}
}