package com.tyranotyrano.web.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyranotyrano.domain.Member;
import com.tyranotyrano.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private MemberRepository memberRepository;

    public Optional<Member> findByName(Long id) {
        return memberRepository.findById(id);
    }
}
