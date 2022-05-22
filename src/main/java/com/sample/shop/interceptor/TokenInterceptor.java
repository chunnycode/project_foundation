package com.sample.shop.interceptor;

import com.sample.shop.login.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

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
