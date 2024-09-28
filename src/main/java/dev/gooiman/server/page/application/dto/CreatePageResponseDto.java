package dev.gooiman.server.page.application.dto;

import dev.gooiman.server.page.repository.entity.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePageResponseDto {

    private String name;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Res {

        private String id;

        public static Res mapEntityToDto(Page pageEntity) {
            return new Res(String.valueOf(pageEntity.getPageId()));
        }
    }
}
