package com.sample.shop.login.service;

import com.sample.shop.login.dto.LoginDto;
import com.sample.shop.login.entity.Member;
import com.sample.shop.login.jwt.TokenProvider;
import com.sample.shop.login.jwt.TokenResponse;
import com.sample.shop.shared.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
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
    public TokenResponse reissueToken(HttpServletRequest request){

        String accessToken = null;
        String refreshToken = tokenProvider.resolveRefreshToken(request);

        if(tokenProvider.isValidRefreshToken(refreshToken)){     //들어온 Refresh 토큰이 유효한지

            log.info("Refresh Token is valid.");
            Claims claimsToken = tokenProvider.getClaimsRefreshToken(refreshToken);

            String memberId = (String)claimsToken.get("memberId");
            Optional<Member> member = memberRepository.findByMemberId(memberId);
            String savedToken =  member.get().getRefreshToken();
            log.info("Saved Token is " + savedToken);

            if(refreshToken.equals(savedToken)) {
                log.info("Access reissued.");
                accessToken = tokenProvider.createAccessToken(memberId);
            } else{
                log.info("Refresh Token Tampered.");
            }
        } else{
            // Refresh Token is not valid.
        }

        return TokenResponse.builder()
                .ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .build();
    }


}
