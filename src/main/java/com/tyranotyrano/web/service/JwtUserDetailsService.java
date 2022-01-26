package com.tyranotyrano.web.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tyranotyrano.domain.Account;
import com.tyranotyrano.domain.repository.AccountRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public JwtUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByName(username)
                                           .orElseThrow(() -> new UsernameNotFoundException("Not Exist Account"));
        return User.builder()
                   .username(account.getName())
                   .password(account.getPassword())
                   .build();
    }
}
