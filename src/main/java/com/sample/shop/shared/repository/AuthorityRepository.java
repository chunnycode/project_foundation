package com.sample.shop.shared.repository;

import com.sample.shop.login.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByMember(Long memberIdx);
}
