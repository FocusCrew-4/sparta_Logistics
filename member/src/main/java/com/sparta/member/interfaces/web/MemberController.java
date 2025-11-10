package com.keepgoing.member.interfaces.web;

import com.keepgoing.member.application.dto.BaseResponseDto;
import com.keepgoing.member.application.dto.LoginDto;
import com.keepgoing.member.application.service.AuthService;
import com.keepgoing.member.application.service.MemberService;
import com.keepgoing.member.interfaces.dto.SignUpRequestDto;
import com.keepgoing.member.interfaces.dto.StatusChangeRequestDto;
import com.keepgoing.member.interfaces.dto.StatusUpdateResponseDto;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestBody LoginDto loginDto
    ) {
        String token = authService.login(loginDto);
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .body(BaseResponseDto.success(null));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
        @RequestBody @Validated SignUpRequestDto requestDto
    ) {
        Long id = memberService.requestSignUp(requestDto);

        URI uri = URI.create("/v1/member/" + id);

        return ResponseEntity.created(uri)
            .body(BaseResponseDto.success(null));
    }

    @PreAuthorize("hasRole('MASTER')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
        @PathVariable Long id,
        @RequestBody StatusChangeRequestDto requestDto
    ) {

        StatusUpdateResponseDto res = memberService.updateStatus(requestDto);

        return ResponseEntity.noContent()
            .build();
    }

}