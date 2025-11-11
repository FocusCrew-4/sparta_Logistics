package com.keepgoing.gateway.dto;

public record MemberResposneDto(
    Long userId,
    String role,
    String status
) {

}
