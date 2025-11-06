package com.sparta.member.domain.repository;

import com.sparta.member.domain.model.Member;
import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByEmail(String email);
    Member save(Member member);
}
