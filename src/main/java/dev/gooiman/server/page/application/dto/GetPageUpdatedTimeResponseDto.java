package dev.gooiman.server.page.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.sql.Timestamp;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GetPageUpdatedTimeResponseDto(Timestamp lastUpdatedTime) {

    public GetPageUpdatedTimeResponseDto(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
