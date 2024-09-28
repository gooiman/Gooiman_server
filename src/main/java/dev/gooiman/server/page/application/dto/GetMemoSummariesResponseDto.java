package dev.gooiman.server.page.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GetMemoSummariesResponseDto(String category,
                                          List<GetSubCategoryResponseDto> subcategories) {

}
