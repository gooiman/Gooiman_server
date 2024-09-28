package dev.gooiman.server.config.security;

import dev.gooiman.server.config.security.fliter.JwtFilter;
import dev.gooiman.server.config.security.provider.JwtAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public JwtConfig(JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    public void configure(HttpSecurity http) {
        JwtFilter jwtFilter = new JwtFilter(jwtAuthenticationProvider);
        http
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
