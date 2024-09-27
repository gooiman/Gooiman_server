package dev.gooiman.server.memo.controller;

import dev.gooiman.server.common.dto.ResponseDto;
import dev.gooiman.server.common.exception.BaseException;
import dev.gooiman.server.common.exception.BaseResponseStatus;
import dev.gooiman.server.memo.application.MemoService;
import dev.gooiman.server.memo.application.dto.CreateMemoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemoController {

    private final MemoService memoService;

    @PutMapping("/memo")
    public ResponseEntity<ResponseDto> createMemo(
        @RequestBody CreateMemoResponseDto createMemoDto) {
        try {
            CreateMemoResponseDto.Res res = memoService.create(createMemoDto);
            return ResponseEntity.status(BaseResponseStatus.SUCCESS.getHttpStatus())
                .body(new ResponseDto<>(BaseResponseStatus.SUCCESS, res));
        } catch (BaseException exception) {
            return ResponseEntity.status(exception.getBaseResponseStatus().getHttpStatus().value())
                .body(new ResponseDto<>(exception.getBaseResponseStatus()));
        }
    }

}
