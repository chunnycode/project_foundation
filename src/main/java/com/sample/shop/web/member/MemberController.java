package com.sample.shop.web.member;

import com.sample.shop.domain.member.dto.MemberJoinDto;
import com.sample.shop.domain.member.entity.Member;
import com.sample.shop.domain.member.repository.MemberRepository;
import com.sample.shop.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

//	private final MemberService memberService;
	private final MemberRepository memberRepository;

	@GetMapping("/add")
	public String addForm(@ModelAttribute("member") Member member){
		return "members/addMemberForm";
	}

	@PostMapping("/add")
	public String save(@Validated @ModelAttribute Member member, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "members/addMemberForm";
		}
		memberRepository.save(member);
		return "redirect:/";
	}


	@PostMapping("/join")
	public ResponseEntity join(@RequestBody MemberJoinDto memberJoinDto) throws Exception {
		return null;
		/*return ResponseEntity
				.ok()
				.body(); // memberService.join(joinDto)*/
	}



}