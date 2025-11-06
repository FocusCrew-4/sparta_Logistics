package com.sparta.member.infrastructure.jwt;


public interface JwtProvider {
    String generateAccessToken(String username, String authorities, String userId);
}
