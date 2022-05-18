package com.sample.shop.member;

import com.sample.shop.dto.MemberRequest;
import com.sample.shop.dto.TokenResponse;
import com.sample.shop.entity.Member;
import com.sample.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface MemberService {
	Optional<Member> findByMemberName(String memberName);
	TokenResponse doLogin(MemberRequest memberRequest) throws Exception;
}
