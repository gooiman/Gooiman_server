package dev.gooiman.server.security.provider;


import static dev.gooiman.server.common.exception.ErrorCode.EXPIRED_TOKEN_ERROR;
import static dev.gooiman.server.common.exception.ErrorCode.INVALID_TOKEN_ERROR;
import static dev.gooiman.server.common.exception.ErrorCode.TOKEN_UNSUPPORTED_ERROR;

import dev.gooiman.server.common.exception.CommonException;
import dev.gooiman.server.security.application.CustomUserDetailsService;
import dev.gooiman.server.security.repository.entity.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.List;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static SecretKey key;

    @Value("${spring.security.secret}")
    private String secret;

    private final CustomUserDetailsService customUserDetailsService;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        try {

            String token = (String) authentication.getPrincipal();

            Jws<Claims> parsedToken = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);

            Claims claims = parsedToken.getPayload();
            String userId = claims.getSubject();

            CustomUserDetails details = customUserDetailsService.loadUserByUserId(userId);

            return new UsernamePasswordAuthenticationToken(details, null,
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        } catch (ExpiredJwtException e) {
            throw new CommonException(EXPIRED_TOKEN_ERROR);
        } catch (JwtException e) {
            throw new CommonException(INVALID_TOKEN_ERROR);
        } catch (IllegalArgumentException e) {
            throw new CommonException(TOKEN_UNSUPPORTED_ERROR);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
