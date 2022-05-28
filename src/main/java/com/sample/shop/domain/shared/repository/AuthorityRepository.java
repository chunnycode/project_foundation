package com.sample.shop.domain.shared.repository;

import com.sample.shop.domain.login.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByMember(Long memberIdx);
}
