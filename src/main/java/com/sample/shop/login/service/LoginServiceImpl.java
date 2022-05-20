package com.sample.shop.login.service;

import com.sample.shop.login.dto.LoginDto;
import com.sample.shop.login.entity.Member;
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

    @Override
    @Transactional
    public TokenResponse doLogin(LoginDto loginDto) throws Exception {

        Member member = memberRepository.findByMemberName(loginDto.getMemberName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!loginDto.getPassword().equals(member.getPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        long ACCESS_TOKEN_VALID_TIME = 1 * 60 * 1000L;   // 1분
        String SECRET_KEY = "Y2h1bm55Y29kZS1zcHJpbmctYm9vdC1qd3QtdHV0b3JpYWwtc2VjcmV0LWNodW5ueWNvZGUtdGVjaC1zcHJpbmctYm9vdC1qd3QtdHV0b3JpYWwtc2VjcmV0";
        Claims claims = Jwts.claims();  // JWT payload 에 저장되는 정보단위
        claims.put("memberId", member.getMemberId());
        Date now = new Date();
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 사용할 암호화 알고리즘과
                .compact();

        return TokenResponse.builder()
                .ACCESS_TOKEN(accessToken)
                .build();
    }

}
