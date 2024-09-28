package dev.gooiman.server.security.controller;

import dev.gooiman.server.common.dto.ResponseDto;
import dev.gooiman.server.security.application.CustomAuthenticationService;
import dev.gooiman.server.security.application.dto.JwtResponseDto;
import dev.gooiman.server.security.application.dto.LoginRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "인증 처리 API")
public class AuthenticationController {

    private final CustomAuthenticationService authenticationService;

    @PostMapping("/login/{page_id}")
    @Operation(summary = "로그인", description = "로그인을 수행합니다. 만약 한번도 로그인 한 적 없는 name으로 로그인을 시도할 경우 회원가입을 수행합니다.")
    public ResponseDto<JwtResponseDto> signIn(@PathVariable("page_id") UUID pageId,
        @RequestBody LoginRequestDto dto) {
        return ResponseDto.ok(authenticationService.login(pageId, dto));
    }
}
