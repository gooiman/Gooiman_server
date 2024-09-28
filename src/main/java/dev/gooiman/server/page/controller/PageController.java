package dev.gooiman.server.page.controller;

import dev.gooiman.server.common.dto.ResponseDto;
import dev.gooiman.server.common.exception.CommonException;
import dev.gooiman.server.memo.application.dto.MemoSummariesResponseDto;
import dev.gooiman.server.page.application.PageService;
import dev.gooiman.server.page.application.dto.CreatePageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/page")
@Tag(name = "Page", description = "페이지 처리 API")
public class PageController {

    private final PageService pageService;

    @PutMapping
    @Operation(summary = "페이지 생성", description = "페이지를 생성합니다.")
    public ResponseDto createPage(
        @RequestBody CreatePageResponseDto createPageDto) {
        CreatePageResponseDto.Res res = pageService.create(createPageDto);
        return ResponseDto.ok(res);
    }

    @GetMapping("/{pageId}")
    @Operation(summary = "페이지 정보 조회", description = "페이지 정보를 조회합니다. 주제 및 소주제로 그룹화된 메모 제목이 같이 제공됩니다.")
    public ResponseDto getPageSummaries(@PathVariable String pageId) {
        MemoSummariesResponseDto.Res res = pageService.memoSummaries(pageId);
        return ResponseDto.ok(res);
    }
}
