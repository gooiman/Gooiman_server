package dev.gooiman.server.page.controller;

import dev.gooiman.server.common.dto.CommonIdResponseDto;
import dev.gooiman.server.common.dto.ResponseDto;
import dev.gooiman.server.memo.application.dto.MemoSummariesResponseDto;
import dev.gooiman.server.page.application.PageService;
import dev.gooiman.server.page.application.dto.CreatePageRequestDto;
import dev.gooiman.server.page.application.dto.GetPageUpdatedTimeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/page")
@Tag(name = "Page", description = "페이지 처리 API")
public class PageController {

    private final PageService pageService;

    @PutMapping
    @Operation(summary = "페이지 생성", description = "페이지를 생성합니다.")
    @SecurityRequirements
    public ResponseDto<CommonIdResponseDto> createPage(
        @RequestBody CreatePageRequestDto createPageDto) {
        return ResponseDto.ok(pageService.create(createPageDto));
    }

    @GetMapping("/{pageId}")
    @Operation(summary = "페이지 정보 조회", description = "페이지 정보를 조회합니다. 주제 및 소주제로 그룹화된 메모 제목이 같이 제공됩니다.")
    public ResponseDto<MemoSummariesResponseDto> getPageSummaries(@PathVariable UUID pageId) {
        MemoSummariesResponseDto res = pageService.memoSummaries(pageId);
        return ResponseDto.ok(res);
    }

    @GetMapping("/{pageId}/last-updated")
    public ResponseDto<GetPageUpdatedTimeResponseDto> getLastUpdatedTime(
        @PathVariable("pageId") UUID pageId) {
        return ResponseDto.ok(pageService.getLastUpdatedPage(pageId));
    }
}
