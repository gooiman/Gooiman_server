package dev.gooiman.server.config.security.provider;


import static dev.gooiman.server.common.exception.ErrorCode.FAILURE_LOGIN;

import dev.gooiman.server.common.exception.CommonException;
import dev.gooiman.server.auth.application.CustomUserDetailsService;
import dev.gooiman.server.auth.application.domain.CustomUserDetails;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public Optional<Authentication> authenticate(UUID pageId, Authentication authentication) {
        String name = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        Optional<CustomUserDetails> userDetail = customUserDetailsService.loadUserByUsername(
            pageId, name);

        if (userDetail.isEmpty()) {
            return Optional.empty();
        }

        userDetail.ifPresent(detail -> validatePassword(password, detail));
        return Optional.of(new UsernamePasswordAuthenticationToken(userDetail.get(), null,
            List.of(new SimpleGrantedAuthority("ROLE_USER"))));
    }

    private void validatePassword(String password, CustomUserDetails userDetails) {
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new CommonException(FAILURE_LOGIN);
        }
    }
}
