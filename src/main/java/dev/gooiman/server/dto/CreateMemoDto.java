package dev.gooiman.server.dto;

import dev.gooiman.server.domain.Memo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateMemoDto {

    private String pageId;
    private String author;
    private String title;
    private String category;
    private String subCategory;
    private String content;

    @Getter
    @Builder
    public static class Res{
        private String pageId;
        private String author;
        private String title;
        private String category;
        private String subCategory;
        private String content;

        public static Res mapEntityToDto(Memo memoEntity) {
            return Res.builder().pageId(String.valueOf(memoEntity.getPage().getPageId()))
                    .author(memoEntity.getUser().getName())
                    .title(memoEntity.getTitle())
                    .category(memoEntity.getCategory())
                    .subCategory(memoEntity.getSubCategory())
                    .content(memoEntity.getContent())
                    .build();
        }
    }

}
