package com.sample.shop.domain.login.service;

import com.sample.shop.domain.login.dto.LoginDto;
import com.sample.shop.domain.shared.jwt.TokenResponse;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    TokenResponse doLogin(LoginDto loginDto) throws Exception;
    TokenResponse issueAccessToken(HttpServletRequest request) throws Exception;
}
