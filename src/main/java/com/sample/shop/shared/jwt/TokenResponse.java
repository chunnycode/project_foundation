package com.sample.shop.shared.jwt;

import lombok.Getter;

@Getter
public class TokenResponse {

	private String accessToken;
	private String refreshToken;

	public static class Builder{

		private String accessToken;
		private String refreshToken;

		public Builder accessToken(String accessToken){
			this.accessToken = accessToken;
			return this;
		}

		public Builder refreshToken(String refreshToken){
			this.refreshToken = refreshToken;
			return this;
		}

		public TokenResponse build(){
			return new TokenResponse(this);
		}
	}

	public TokenResponse(Builder builder){
		this.accessToken = builder.accessToken;
		this.refreshToken = builder.refreshToken;
	}

}
