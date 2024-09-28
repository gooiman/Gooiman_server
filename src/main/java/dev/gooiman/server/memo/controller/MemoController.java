package dev.gooiman.server.memo.controller;

import dev.gooiman.server.common.dto.CommonIdResponseDto;
import dev.gooiman.server.common.dto.CommonSuccessDto;
import dev.gooiman.server.common.dto.ResponseDto;
import dev.gooiman.server.memo.application.MemoService;
import dev.gooiman.server.memo.application.dto.CreateMemoRequestDto;
import dev.gooiman.server.memo.application.dto.GetMemoResponseDto;
import dev.gooiman.server.memo.application.dto.UpdateMemoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memo")
public class MemoController {

    private final MemoService memoService;

    @PatchMapping("/{memoId}")
    public ResponseDto<CommonSuccessDto> updateMemo(@PathVariable("memoId") String memoId,
        @RequestBody UpdateMemoRequestDto dto) {
        return ResponseDto.ok(memoService.updateMemo(memoId, dto));
    }

    @PutMapping("")
    public ResponseDto<CommonIdResponseDto> createMemo(@RequestBody CreateMemoRequestDto dto) {
        return ResponseDto.created(memoService.createMemo(dto));
    }

    @GetMapping("/{memo_id}")
    public ResponseDto<GetMemoResponseDto> getMemo(@PathVariable("memo_id") String memoId) {
        return ResponseDto.ok(memoService.getMemo(memoId));
    }
}
