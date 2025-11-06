package com.sparta.member.infrastructure.persistence.jpa.repo;

import com.sparta.member.infrastructure.persistence.jpa.entity.MemberJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMemberJpaRepository extends JpaRepository<MemberJpa,Long> {

}
