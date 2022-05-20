package com.sample.shop.member.service;

import com.sample.shop.member.dto.LoginDto;
import com.sample.shop.member.jwt.TokenResponse;
import com.sample.shop.member.entity.Member;

import java.util.Optional;

public interface MemberService {
	Optional<Member> findByMemberName(String memberName);
	TokenResponse doLogin(LoginDto loginDto) throws Exception;
}
