package com.sample.shop.login.entity;

import com.sample.shop.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "authority")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Authority {


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