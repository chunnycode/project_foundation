package com.sample.shop.shared.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
	private String accessToken;
	private String refreshToken;
}
