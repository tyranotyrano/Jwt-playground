package com.tyranotyrano.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyranotyrano.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
