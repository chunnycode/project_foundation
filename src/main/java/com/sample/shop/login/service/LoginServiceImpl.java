package com.sample.shop.login.service;

import com.sample.shop.login.dto.LoginDto;
import com.sample.shop.member.entity.Member;
import com.sample.shop.shared.jwt.TokenProvider;
import com.sample.shop.shared.jwt.TokenResponse;
import com.sample.shop.shared.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public LoginServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    @Transactional
    public TokenResponse doLogin(LoginDto loginDto) throws Exception {

        Member member = memberRepository.findByMemberId(loginDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = tokenProvider.createAccessToken(member.getMemberId());
        String refreshToken = tokenProvider.createRefreshToken(member.getMemberId());

        member.refreshUpdate(refreshToken);

        return new TokenResponse
                .Builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public TokenResponse issueAccessToken(HttpServletRequest request) throws Exception{

        String accessToken = tokenProvider.resolveAccessToken(request);
        String refreshToken = tokenProvider.resolveRefreshToken(request);

        // TODO Expired만 된건지 확인하고싶으면 ? Claims의 정보로 확인하고자 했으나 변환시 바로 Exception을 던지기 때문에 다른 방법 필요
        /*if(! tokenProvider.isOnlyExpiredAccessToken(accessToken)){
            throw new Exception("Access Token이 유효하지 않습니다.");
        }*/

        if(tokenProvider.isValidRefreshToken(refreshToken)){ // Refresh 토큰이 유효한지
            Claims refreshClaims = tokenProvider.getClaimsRefreshToken(refreshToken);
            String memberId = (String)refreshClaims.get("memberId");
            accessToken = tokenProvider.createAccessToken(memberId);
        } else{
            throw new Exception("Refresh Token이 유효하지 않습니다.");
        }

        return new TokenResponse
                .Builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


}
