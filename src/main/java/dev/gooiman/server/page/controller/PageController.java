package dev.gooiman.server.page.controller;

import dev.gooiman.server.common.dto.ResponseDto;
import dev.gooiman.server.page.application.PageService;
import dev.gooiman.server.page.application.dto.CreatePageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api")
public class PageController {

    private final PageService pageService;

    @PutMapping("/page")
    public ResponseEntity<ResponseDto> createPage(
        @RequestBody CreatePageResponseDto createPageDto) {
        CreatePageResponseDto.Res res = pageService.create(createPageDto);
//        return ResponseEntity.status(BaseResponseStatus.SUCCESS.getHttpStatus().value()).body(new ResponseDto(BaseResponseStatus.SUCCESS, res));
        return null;
    }

    @GetMapping("/page/{pageId}")
    public ResponseEntity<ResponseDto> getPageSummarise(@PathVariable String pageId) {
//        pageService.get
        return null;
    }
}
