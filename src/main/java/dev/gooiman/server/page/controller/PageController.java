package dev.gooiman.server.page.controller;

import dev.gooiman.server.page.application.dto.CreatePageResponseDto;
import dev.gooiman.server.common.dto.ResponseDto;
import dev.gooiman.server.common.exception.BaseResponseStatus;
import dev.gooiman.server.page.application.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("/api")
public class PageController {

    private final PageService pageService;

    @PutMapping("/page")
    public ResponseEntity<ResponseDto> createPage(@RequestBody CreatePageResponseDto createPageDto) {
        CreatePageResponseDto.Res res = pageService.create(createPageDto);
        return ResponseEntity.status(BaseResponseStatus.SUCCESS.getHttpStatus().value()).body(new ResponseDto(BaseResponseStatus.SUCCESS, res));
    }

    @GetMapping("/page/{pageId}")
    public ResponseEntity<ResponseDto> getPageSummarise(@PathVariable String pageId) {
        pageService.get
    }
}
