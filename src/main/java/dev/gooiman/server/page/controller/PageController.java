package dev.gooiman.server.page.controller;

import dev.gooiman.server.page.application.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/page")
public class PageController {

    private final PageService pageService;

//    @PutMapping("/page")
//    public ResponseEntity<ResponseDto> createPage(
//        @RequestBody CreatePageResponseDto createPageDto) {
//        CreatePageResponseDto.Res res = pageService.create(createPageDto);
////        return ResponseEntity.status(BaseResponseStatus.SUCCESS.getHttpStatus().value()).body(new ResponseDto(BaseResponseStatus.SUCCESS, res));
//        return null;
//    }
//
//    @GetMapping("/page/{pageId}")
//    public ResponseEntity<ResponseDto> getPageSummarise(@PathVariable String pageId) {
////        pageService.get
//        return null;
//    }
}
