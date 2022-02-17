package com.tyranotyrano.web.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyranotyrano.domain.Account;
import com.tyranotyrano.domain.repository.AccountRepository;
import com.tyranotyrano.dto.RegisterAccountRq;

@Service
@Transactional(readOnly = true)
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository,
                          PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterAccountRq rq) {
        accountRepository.save(rq.toDto());
    }

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }
}
