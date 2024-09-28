package dev.gooiman.server.common.dto;

import java.util.UUID;

public record CommonIdResponseDto(String id) {

    public CommonIdResponseDto(UUID id) {
        this(id.toString());
    }
}
