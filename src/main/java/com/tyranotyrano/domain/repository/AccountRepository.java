package com.tyranotyrano.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyranotyrano.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByName(String name);
    boolean existsByName(String name);
}
