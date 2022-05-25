package com.sample.shop.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {

	@Id
	@Column(name = "member_idx")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberIdx;

	@Column(name = "member_id", length = 50, unique = true)
	private String memberId;

	@Column(name = "member_name", length = 50, unique = true)
	private String memberName;

	@Column(name = "password", length = 100)
	private String password;

	private String refreshToken;

	public void refreshUpdate(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
