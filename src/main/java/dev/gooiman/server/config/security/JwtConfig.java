package dev.gooiman.server.config.security;

import dev.gooiman.server.config.security.fliter.JwtFilter;
import dev.gooiman.server.config.security.provider.JwtProvider;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Getter
    @Value("${spring.security.blacklist-validity-time")
    private Long blacklistValidityTime;

    private final JwtProvider jwtAuthenticationProvider;

    public JwtConfig(JwtProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    public void configure(HttpSecurity http) {
        JwtFilter jwtFilter = new JwtFilter(jwtAuthenticationProvider);
        http
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
