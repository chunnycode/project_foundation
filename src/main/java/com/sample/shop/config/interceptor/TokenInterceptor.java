package com.sample.shop.config.interceptor;

import com.sample.shop.domain.shared.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    public TokenInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("Token Intercepter Called.");
        String accessToken = request.getHeader("accessToken");
        log.info("AccessToken is " + accessToken);
        String refreshToken = request.getHeader("refreshToken");
        log.info("RefreshToken is " + refreshToken);

        if(accessToken != null && tokenProvider.isValidAccessToken(accessToken)){
            return true;
        }

        response.setStatus(401);
        response.setHeader("accessToken", accessToken);
        response.setHeader("refreshToken", refreshToken);
        response.setHeader("msg", "Token is invalid.");
        return false;
    }
}
