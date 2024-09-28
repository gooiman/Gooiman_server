package dev.gooiman.server.memo.controller;

import dev.gooiman.server.memo.application.MemoService;
import lombok.RequiredArgsConstructor;
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
}
