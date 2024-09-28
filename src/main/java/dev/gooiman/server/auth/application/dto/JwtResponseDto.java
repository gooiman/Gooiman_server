package dev.gooiman.server.auth.application.dto;


import lombok.Builder;

@Builder
public record JwtResponseDto(String token) {

    public JwtResponseDto(String token) {
        this.token = "Bearer " + token;
    }
}
