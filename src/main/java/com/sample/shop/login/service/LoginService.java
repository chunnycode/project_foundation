package com.sample.shop.login.service;

import com.sample.shop.login.dto.LoginDto;
import com.sample.shop.login.jwt.TokenResponse;

public interface LoginService {
    TokenResponse doLogin(LoginDto loginDto) throws Exception;

}
