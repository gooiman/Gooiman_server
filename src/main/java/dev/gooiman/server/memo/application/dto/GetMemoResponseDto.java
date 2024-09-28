package dev.gooiman.server.memo.application.dto;

import java.util.UUID;

public record GetMemoResponseDto(String id, String title, String content, String author,
                                 String category, String subCategory) {

    public GetMemoResponseDto(UUID id, String title, String content, String author,
        String category,
        String subCategory) {
        this(id.toString(), title, content, author, category, subCategory);
    }
}
