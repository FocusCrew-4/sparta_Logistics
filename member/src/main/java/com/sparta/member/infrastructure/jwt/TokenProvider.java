package com.sparta.member.infrastructure.jwt;


import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

@RequiredArgsConstructor
public class TokenProvider implements JwtProvider {

    private final JwtEncoder jwtEncoder;

    @Override
    public String generateAccessToken(String username, String authorities, String userId) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .subject(username)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(1800))
            .claim("role", authorities)
            .claim("userId", userId)
            .build();

        JwsHeader header = JwsHeader.with(() -> "HS256").build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims))
            .getTokenValue();
    }
}
