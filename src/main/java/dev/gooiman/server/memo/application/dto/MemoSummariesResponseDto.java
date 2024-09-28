package dev.gooiman.server.memo.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.gooiman.server.memo.application.domain.MemoSummaryList;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MemoSummariesResponseDto(String name, MemoSummaryList memoSummaries) {

}
