package dev.gooiman.server.memo.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import java.util.Map;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MemoSummariesResponseDto(String name,
                                       Map<String, Map<String, List<String>>> memoSummaries) {

}
