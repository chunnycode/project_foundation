package com.sample.shop.member.repository;

import com.sample.shop.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByMemberName(String memberName);
}
