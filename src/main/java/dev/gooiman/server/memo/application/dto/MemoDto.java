package dev.gooiman.server.memo.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.gooiman.server.memo.repository.entity.Memo;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MemoDto(
    UUID id,
    String title,
    String content,
    String author,
    String category,
    String subCategory,
    String color
) {

  public static MemoDto fromEntity(Memo memo) {
    return new MemoDto(
        memo.getMemoId(),
        memo.getTitle(),
        memo.getContent(),
        memo.getUser().getName(),
        memo.getCategory(),
        memo.getSubCategory(),
        memo.getColor()
    );
  }
}
