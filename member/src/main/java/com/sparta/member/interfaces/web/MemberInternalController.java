package com.sparta.member.interfaces.web;

import com.sparta.member.application.service.MemberService;
import com.sparta.member.infrastructure.userDetails.CustomUserDetails;
import com.sparta.member.interfaces.dto.BaseResponseDto;
import com.sparta.member.interfaces.dto.response.MemberInfoInternalResponseDto;
import com.sparta.member.interfaces.dto.response.MemberInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/internal/member")
public class MemberInternalController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<BaseResponseDto<?>> getMember(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        MemberInfoInternalResponseDto res = memberService.memberInfoForGateway(userDetails.getUserId());

        return ResponseEntity.ok(
            BaseResponseDto.success(res)
        );
    }

}
