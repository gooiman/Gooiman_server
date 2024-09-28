package dev.gooiman.server.memo.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record CreateMemoRequestDto(@JsonProperty(value = "page_id") UUID pageId,
                                   String author,
                                   String title,
                                   String category,
                                   @JsonProperty(value = "sub_category") String subCategory,
                                   String content) {


}
