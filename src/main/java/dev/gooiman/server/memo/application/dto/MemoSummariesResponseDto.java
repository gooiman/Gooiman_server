package dev.gooiman.server.memo.application.dto;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemoSummariesResponseDto {


    @Getter
    @AllArgsConstructor
    @Builder
    public static class Res {

        private String name;
        private Map<String, Map<String, List<String>>> memoSummaries;
    }
}
