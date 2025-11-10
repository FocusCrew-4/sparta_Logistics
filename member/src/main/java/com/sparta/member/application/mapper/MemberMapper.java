package com.keepgoing.member.application.mapper;

import com.keepgoing.member.domain.model.Member;
import com.keepgoing.member.interfaces.dto.StatusUpdateResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper implements ApplicationMapper {

    @Override
    public StatusUpdateResponseDto toStatusUpdateResponseDto(Member savedMember) {
        return new StatusUpdateResponseDto(
            savedMember.accountInfo().email(),
            savedMember.accountInfo().name(),
            savedMember.status()
        );
    }
}
