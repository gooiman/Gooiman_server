package dev.gooiman.server.memo.controller;

import dev.gooiman.server.common.dto.CommonIdResponseDto;
import dev.gooiman.server.common.dto.CommonSuccessDto;
import dev.gooiman.server.common.dto.ResponseDto;
import dev.gooiman.server.memo.application.MemoService;
import dev.gooiman.server.memo.application.dto.MemoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import dev.gooiman.server.memo.application.dto.CreateMemoRequestDto;
import dev.gooiman.server.memo.application.dto.GetMemoResponseDto;
import dev.gooiman.server.memo.application.dto.UpdateMemoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memo")
@Tag(name = "Memo", description = "메모 처리 API")
public class MemoController {

    private final MemoService memoService;


    @GetMapping("")
    @Operation(summary = "메모 목록 조회", description = "페이지에 속한 메모 목록을 조회합니다. 카테고리를 지정하면 해당 카테고리에 속한 메모만 조회합니다.")
    public ResponseDto<MemoDto[]> listMemo(@RequestParam("page_id") UUID pageId, @RequestParam(required = false) String category) {
        return ResponseDto.ok(memoService.listMemo(pageId, category));
    }

    @PatchMapping("/{memoId}")
    @Operation(summary = "메모 수정", description = "메모를 수정합니다. 메모 작성자만 수정이 가능합니다.")
    public ResponseDto<CommonSuccessDto> updateMemo(@PathVariable("memoId") String memoId,
        @RequestBody UpdateMemoRequestDto dto) {
        return ResponseDto.ok(memoService.updateMemo(memoId, dto));
    }

    @PutMapping("")
    @Operation(summary = "메모 생성", description = "메모를 생성합니다.")
    public ResponseDto<CommonIdResponseDto> createMemo(@RequestBody CreateMemoRequestDto dto) {
        return ResponseDto.created(memoService.createMemo(dto));
    }

    @GetMapping("/{memoId}")
    @Operation(summary = "메모 조회", description = "메모를 조회합니다.")
    public ResponseDto<GetMemoResponseDto> getMemo(@PathVariable("memoId") String memoId) {
        return ResponseDto.ok(memoService.getMemo(memoId));
    }

    @DeleteMapping("/{memoId}")
    @Operation(summary = "메모 삭제", description = "메모를 삭제합니다. 메모 작성자만 삭제가 가능합니다.")
    public ResponseDto<CommonSuccessDto> deleteMemo(@PathVariable("memoId") String memoId) {
        return ResponseDto.ok(memoService.deleteMemo(memoId));
    }
}
