package dev.gooiman.server.memo.controller;

import dev.gooiman.server.common.dto.ResponseDto;
import dev.gooiman.server.common.exception.CommonException;
import dev.gooiman.server.memo.application.MemoService;
import dev.gooiman.server.memo.application.dto.CreateMemoDto;
import dev.gooiman.server.memo.application.dto.GetMemoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memo")
public class MemoController {

    private final MemoService memoService;

    @PutMapping()
    public ResponseDto createMemo(
            @RequestBody CreateMemoDto createMemoDto) {
        try {
            CreateMemoDto.Res res = memoService.create(createMemoDto);
            return ResponseDto.ok(res);
        } catch (CommonException exception) {
            return ResponseDto.fail(exception);
        }
    }

    @GetMapping("/{memoId}")
    public ResponseDto getMemo(@PathVariable String memoId) {
        try {
            GetMemoDto.Res memo = memoService.getMemo(memoId);
            return ResponseDto.ok(memo);
        } catch (CommonException exception) {
            return ResponseDto.fail(exception);
        }
    }
}
