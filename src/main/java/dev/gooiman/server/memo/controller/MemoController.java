package dev.gooiman.server.memo.controller;

import dev.gooiman.server.common.dto.ResponseDto;
import dev.gooiman.server.memo.application.MemoService;
import dev.gooiman.server.memo.application.dto.MemoDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memo")
public class MemoController {

    private final MemoService memoService;


    @GetMapping("/")
    public ResponseDto<MemoDto[]> listMemo(@RequestParam("page_id") UUID pageId, @RequestParam(required = false) String category) {
        return ResponseDto.ok(memoService.listMemo(pageId, category));
    }

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
