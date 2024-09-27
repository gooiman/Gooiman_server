package dev.gooiman.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class MemoSummariseDto {


    @Getter
    @AllArgsConstructor
    @Builder
    public static class Res{
        private String name;
        private Map<String, Map<String, List<String>>> memoSummarise;
    }
}
