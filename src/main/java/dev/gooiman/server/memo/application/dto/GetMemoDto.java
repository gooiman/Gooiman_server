package dev.gooiman.server.memo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class GetMemoDto {

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Res{
        private String id;
        private String title;
        private String content;
        private String author;
        private String category;
        private String sub_category;
    }
}
