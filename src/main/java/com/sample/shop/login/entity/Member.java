package com.sample.shop.login.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
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

	@Column(name = "activated")
	private boolean activated;

	@ManyToMany
	@JoinTable(
			name = "member_authority",
			joinColumns = {@JoinColumn(name = "member_idx", referencedColumnName = "member_idx")},
			inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
	private Set<Authority> authorities;
}
