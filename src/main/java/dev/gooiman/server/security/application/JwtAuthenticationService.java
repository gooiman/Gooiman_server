package dev.gooiman.server.security.application;


import dev.gooiman.server.security.repository.entity.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private static SecretKey key;

    @Value("${spring.security.secret}")
    private String secret;

    @Value("${spring.security.token-validity-time}")
    private int validityTime;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        UUID userId = principal.getId();
        return getAccessToken(userId);
    }

    private String getAccessToken(UUID memberId) {
        long now = (new Date()).getTime();
        Date tokenExpirationDate = new Date(now + validityTime);

        return Jwts.builder()
            .signWith(key)
            .subject(String.valueOf(memberId))
            .expiration(tokenExpirationDate)
            .issuedAt(new Date(now))
            .compact();
    }
}
