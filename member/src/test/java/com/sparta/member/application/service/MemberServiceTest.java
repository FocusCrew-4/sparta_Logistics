package com.sparta.member.application.service;

import com.sparta.member.domain.model.Member;
import com.sparta.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class) // JUnit5 에서 Mockito 활성화
class MemberServiceTest {

    // 다른 주입받는 Repo
    @Mock
    MemberRepository memberRepository;

    // 실제 테스트 대상
    @InjectMocks
    MemberService memberService;

    @Test
    void requestSignUp() {
        //given

        //when

        //then


    }
}