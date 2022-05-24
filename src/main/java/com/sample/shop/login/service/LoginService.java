package com.sample.shop.login.service;

import com.sample.shop.login.dto.LoginDto;
import com.sample.shop.shared.jwt.TokenResponse;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    TokenResponse doLogin(LoginDto loginDto) throws Exception;
    TokenResponse issueAccessToken(HttpServletRequest request) throws Exception;
}
