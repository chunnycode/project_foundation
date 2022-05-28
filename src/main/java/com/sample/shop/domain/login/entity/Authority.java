package com.sample.shop.domain.login.entity;

import com.sample.shop.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "authority")
@NoArgsConstructor
public class Authority {

	// 권한에 따른 Security 구현을 위해 남겨둠

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(name = "authority_name", length = 50)
	private String authorityName;

	private String accessToken;
	private String refreshToken;

	@ManyToOne
	@JoinColumn(name = "member_idx")
	private Member member;

	public void accessUpdate(String accessToken) {
		this.accessToken = accessToken;
	}

	public void refreshUpdate(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}