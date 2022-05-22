package com.sample.shop.login.service;

import com.sample.shop.login.dto.LoginDto;
import com.sample.shop.login.jwt.TokenResponse;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    TokenResponse doLogin(LoginDto loginDto) throws Exception;
    TokenResponse reissueToken(HttpServletRequest request);
}
