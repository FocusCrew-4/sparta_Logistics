package com.keepgoing.member.application.service;

import com.keepgoing.member.domain.enums.Status;
import com.keepgoing.member.domain.vo.Affiliation;
import com.keepgoing.member.interfaces.dto.SignUpRequestDto;
import com.keepgoing.member.application.mapper.ApplicationMapper;
import com.keepgoing.member.domain.model.Member;
import com.keepgoing.member.domain.repository.MemberRepository;
import com.keepgoing.member.global.CustomException;
import com.keepgoing.member.global.ErrorCode;
import com.keepgoing.member.interfaces.dto.StatusChangeRequestDto;
import com.keepgoing.member.interfaces.dto.StatusUpdateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ApplicationMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public Long requestSignUp(SignUpRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.email())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        Member newMember = Member.requestSignUp(
            requestDto.name(),
            passwordEncoder.encode(requestDto.password()),
            requestDto.email(),
            requestDto.slackId(),
            new Affiliation(
                requestDto.affiliation_Type(),
                requestDto.affiliation_Id(),
                requestDto.affiliation_name()
            ),
            requestDto.role()
        );
        Member savedMember = memberRepository.save(newMember);
        return savedMember.id();
    }

    public StatusUpdateResponseDto updateStatus(StatusChangeRequestDto requestDto) {
        Member targetMember = memberRepository.findByEmail(requestDto.email());
        switch(requestDto.status()) {
            case APPROVED -> targetMember.approve();
            case REJECTED -> targetMember.reject();
        }
        Member savedMember = memberRepository.save(targetMember);
        return mapper.toStatusUpdateResponseDto(savedMember);
    }
}
