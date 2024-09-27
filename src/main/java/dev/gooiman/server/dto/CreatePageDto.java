package dev.gooiman.server.dto;

import dev.gooiman.server.domain.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePageDto {
    private String name;

    @Getter
    @AllArgsConstructor
    public static class Res {
        private String id;

        public static Res mapEntityToDto(Page pageEntity) {
            return new Res(String.valueOf(pageEntity.getPageId()));
        }
    }
}
