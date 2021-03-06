package com.crio.cred.security;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;

/**
 * The type Jwt provider.
 *
 * @author harikesh.pallantla
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtProvider {
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    /**
     * Generate jwt token string.
     *
     * @param emailId the email id
     * @return the string
     */
    public String generateJwtToken(String emailId) {
        return Jwts.builder()
                .setSubject(emailId)
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();
    }
}
