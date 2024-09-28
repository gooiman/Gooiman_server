package dev.gooiman.server.common.controller;

import dev.gooiman.server.common.dto.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

    @GetMapping("")
    public ResponseDto<String> healthCheck() {
        return ResponseDto.ok("ok");
    }
}
