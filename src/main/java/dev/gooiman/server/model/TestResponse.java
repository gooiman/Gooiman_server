package dev.gooiman.server.model;

import lombok.Data;

@Data
public class TestResponse {
    private final boolean success;
    private final String message;
}
