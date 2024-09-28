package dev.gooiman.server.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/auth")
@Tag(name = "User", description = "사용자 처리 API")
public class UserController {

//    @PostMapping("/login/{pageId}")
//    public ResponseEntity<ResponseDto> login(@PathVariable("pageId") String pageId, @RequestBody LoginDto loginDto) {
//
//    }
}
