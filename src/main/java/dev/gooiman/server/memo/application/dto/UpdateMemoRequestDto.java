package dev.gooiman.server.memo.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UpdateMemoRequestDto(String title,
                                   String content,
                                   String author,
                                   String category,
                                   String subCategory,
                                   String color
) {

}
