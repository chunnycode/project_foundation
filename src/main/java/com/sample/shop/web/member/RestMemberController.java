package com.sample.shop.web.member;

import com.sample.shop.domain.member.dto.MemberJoinDto;
import com.sample.shop.domain.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/member")
public class RestMemberController {

	private final MemberService memberService;

	public RestMemberController(MemberService memberService) {
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