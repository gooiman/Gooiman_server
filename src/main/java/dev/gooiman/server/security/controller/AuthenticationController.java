package dev.gooiman.server.security.controller;

import dev.gooiman.server.common.dto.ResponseDto;
import dev.gooiman.server.security.application.CustomAuthenticationService;
import dev.gooiman.server.security.application.dto.JwtResponseDto;
import dev.gooiman.server.security.application.dto.LoginRequestDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final CustomAuthenticationService authenticationService;

    @PostMapping("/login/{page_id}")
    public ResponseDto<JwtResponseDto> signIn(@PathVariable("page_id") UUID pageId,
        @RequestBody LoginRequestDto dto) {
        return ResponseDto.ok(authenticationService.login(pageId, dto));
    }
}
