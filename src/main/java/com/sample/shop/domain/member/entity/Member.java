package com.sample.shop.domain.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class Member {
	private long id;

	@NotEmpty
	private String loginId;
	@NotEmpty
	private String name;
	@NotEmpty
	private String password;
}
