package dev.gooiman.server.controller;

import dev.gooiman.server.dto.CreatePageDto;
import dev.gooiman.server.dto.ResponseDto;
import dev.gooiman.server.exception.BaseResponseStatus;
import dev.gooiman.server.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("/api")
public class PageController {

    private final PageService pageService;

    @PutMapping("/page")
    public ResponseEntity<ResponseDto> createPage(@RequestBody CreatePageDto createPageDto) {
        CreatePageDto.Res res = pageService.create(createPageDto);
        return ResponseEntity.status(BaseResponseStatus.SUCCESS.getHttpStatus().value()).body(new ResponseDto(BaseResponseStatus.SUCCESS, res));
    }

    @GetMapping("/page/{pageId}")
    public ResponseEntity<ResponseDto> getPageSummarise(@PathVariable String pageId) {
        pageService.get
    }
}
