package com.sample.shop.domain.member.repository;

import com.sample.shop.domain.member.entity.Member2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJPARepository extends JpaRepository<Member2, Long> {
	Optional<Member2> findByMemberId(String memberId);
}
