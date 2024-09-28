package dev.gooiman.server.common.controller;

import dev.gooiman.server.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Hidden
public class HealthCheckController {

    @GetMapping("")
    public ResponseDto<String> healthCheck() {
        return ResponseDto.ok("ok");
    }
}
