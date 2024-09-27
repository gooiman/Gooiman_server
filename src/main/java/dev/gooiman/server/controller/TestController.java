package dev.gooiman.server.controller;

import dev.gooiman.server.model.TestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public TestResponse test() {
        return new TestResponse(true, "Hello, world!");
    }
}
