package dev.gooiman.server.memo.controller;

import dev.gooiman.server.common.dto.CommonSuccessDto;
import dev.gooiman.server.common.dto.ResponseDto;
import dev.gooiman.server.memo.application.MemoService;
import dev.gooiman.server.memo.application.dto.UpdateMemoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memo")
public class MemoController {

    private final MemoService memoService;

//    @PutMapping("/memo")
//    public ResponseEntity<ResponseDto> createMemo(
//        @RequestBody CreateMemoResponseDto createMemoDto) {
//        try {
//            CreateMemoResponseDto.Res res = memoService.create(createMemoDto);
//            return ResponseEntity.status(BaseResponseStatus.SUCCESS.getHttpStatus())
//                .body(new ResponseDto<>(BaseResponseStatus.SUCCESS, res));
//        } catch (BaseException exception) {
//            return ResponseEntity.status(exception.getBaseResponseStatus().getHttpStatus().value())
//                .body(new ResponseDto<>(exception.getBaseResponseStatus()));
//        }
//        return null;
//    }

    @PatchMapping("/{memoId}")
    public ResponseDto<CommonSuccessDto> updateMemo(@PathVariable("memoId") String memoId,
        @RequestBody UpdateMemoRequestDto dto) {
        return ResponseDto.ok(memoService.updateMemo(memoId, dto));
    }

    @DeleteMapping("/{memoId}")
    public ResponseDto<CommonSuccessDto> deleteMemo(@PathVariable("memoId") String memoId) {
        return ResponseDto.ok(memoService.deleteMemo(memoId));
    }
}
