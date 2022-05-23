package com.sample.shop.login.service;

import com.sample.shop.login.dto.LoginDto;
import com.sample.shop.login.entity.Member;
import com.sample.shop.login.jwt.TokenProvider;
import com.sample.shop.login.jwt.TokenResponse;
import com.sample.shop.shared.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LoginServiceImpl implements LoginService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

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

        member.accessUpdate(accessToken);
        member.refreshUpdate(refreshToken);

        return TokenResponse.builder()
                .ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public TokenResponse issueAccessToken(HttpServletRequest request) throws Exception{

        String accessToken = null;
        String refreshToken = tokenProvider.resolveRefreshToken(request);

        if(tokenProvider.isValidRefreshToken(refreshToken)){ // Refresh 토큰 자체가 유효한지

            log.info("Refresh Token is valid.");
            Claims claimsToken = tokenProvider.getClaimsRefreshToken(refreshToken);

            String memberId = (String)claimsToken.get("memberId");
            Member member = memberRepository.findByMemberId(memberId).orElseThrow(IllegalArgumentException::new);
            String savedToken =  member.getRefreshToken();
            log.info("Saved Token is " + savedToken);

            if(refreshToken.equals(savedToken)) { // Refresh Token이 저장된 값과 동일한지
                log.info("Access reissued.");
                accessToken = tokenProvider.createAccessToken(memberId);
                member.accessUpdate(accessToken);
            } else {
                log.info("Refresh Token Tampered.");
                throw new Exception("Refresh Token 불일치합니다.");
            }
        } else{
            throw new Exception("Refresh Token이 유효하지 않습니다.");
        }

        return TokenResponse.builder()
                .ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .build();
    }


}
