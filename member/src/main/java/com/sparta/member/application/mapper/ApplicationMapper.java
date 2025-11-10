package com.keepgoing.member.application.mapper;

import com.keepgoing.member.domain.model.Member;
import com.keepgoing.member.interfaces.dto.StatusUpdateResponseDto;

public interface ApplicationMapper {

    StatusUpdateResponseDto toStatusUpdateResponseDto(Member savedMember);
}
