package com.sparta.member.infrastructure.persistence;

import com.sparta.member.domain.model.Member;
import com.sparta.member.domain.repository.MemberRepository;
import com.sparta.member.infrastructure.persistence.jpa.entity.MemberJpa;
import com.sparta.member.infrastructure.persistence.jpa.repo.SpringDataMemberJpaRepository;
import com.sparta.member.infrastructure.persistence.mapper.MemberJpaMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final SpringDataMemberJpaRepository springDataMemberJpaRepository;
    private final MemberJpaMapper mapper;

    @Override
    public Optional<MemberJpa> findByEmailUseInfra(String email) {
        return springDataMemberJpaRepository.findByEmail(email);
    }

    @Override
    public Member save(Member member) {
        return mapper.toMember(springDataMemberJpaRepository.save(mapper.toMemberJpa(member)));
    }
}
