package dev.gooiman.server.page.controller;

import dev.gooiman.server.common.dto.ResponseDto;
import dev.gooiman.server.memo.application.dto.MemoSummariseResponseDto;
import dev.gooiman.server.page.application.PageService;
import dev.gooiman.server.page.application.dto.CreatePageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/page")
public class PageController {

    private final PageService pageService;

    @PutMapping
    public ResponseDto createPage(
        @RequestBody CreatePageResponseDto createPageDto) {
        CreatePageResponseDto.Res res = pageService.create(createPageDto);
        return ResponseDto.ok(res);
    }

    @GetMapping("/{pageId}")
    public ResponseDto getPageSummarise(@PathVariable String pageId) {
        MemoSummariseResponseDto.Res res = pageService.memoSummarise(UUID.fromString(pageId));
        return ResponseDto.ok(res);
    }
}
