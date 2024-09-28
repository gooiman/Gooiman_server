package dev.gooiman.server.memo.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateMemoRequestDto(UUID pageId,
                                   String author,
                                   String title,
                                   String category,
                                   String subCategory,
                                   String content,
                                   String color
) {


}
