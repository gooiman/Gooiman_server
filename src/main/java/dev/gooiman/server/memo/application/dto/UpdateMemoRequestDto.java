package dev.gooiman.server.memo.application.dto;

public record UpdateMemoRequestDto(String title,
                                   String content,
                                   String author,
                                   String category,
                                   String subCategory,
                                   String color
                                   ) {

}
