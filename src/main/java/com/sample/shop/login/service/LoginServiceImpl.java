package com.sample.shop.login.service;

import com.sample.shop.login.dto.LoginDto;
import com.sample.shop.login.entity.Member;
import com.sample.shop.login.jwt.TokenProvider;
import com.sample.shop.login.jwt.TokenResponse;
import com.sample.shop.shared.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Override
    @Transactional
    public TokenResponse doLogin(LoginDto loginDto) throws Exception {

        Member member = memberRepository.findByMemberName(loginDto.getMemberName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!loginDto.getPassword().equals(member.getPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = tokenProvider.createToken(member.getMemberName());

        return TokenResponse.builder()
                .ACCESS_TOKEN(accessToken)
                .build();
    }

}
