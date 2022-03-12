package com.tyranotyrano.web.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyranotyrano.domain.user.User;
import com.tyranotyrano.domain.user.UserRole;
import com.tyranotyrano.domain.user.repository.UserRepository;
import com.tyranotyrano.web.rqrs.SignUpDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                             .map(this::createUser)
                             .orElseThrow(() -> new UsernameNotFoundException(String.format("Email 이 올바르지 않습니다. email=%s", email)));
    }

    @Transactional
    public User signup(SignUpDto signUpDto) {
        validateExistUser(signUpDto.getEmail());
        User user = User.of(signUpDto.getEmail(), passwordEncoder.encode(signUpDto.getPassword()), UserRole.USER.name());

        return userRepository.save(user);
    }

    private void validateExistUser(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
    }

    private org.springframework.security.core.userdetails.User createUser(User user) {
        List<GrantedAuthority> grantedAuthorities = user.getRoles()
                                                        .stream()
                                                        .map(SimpleGrantedAuthority::new)
                                                        .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                                                                      user.getPassword(),
                                                                      grantedAuthorities);
    }
}
