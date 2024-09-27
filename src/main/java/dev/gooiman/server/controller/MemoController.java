package dev.gooiman.server.controller;

import dev.gooiman.server.dto.CreateMemoDto;
import dev.gooiman.server.dto.ResponseDto;
import dev.gooiman.server.exception.BaseException;
import dev.gooiman.server.exception.BaseResponseStatus;
import dev.gooiman.server.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemoController {

    private final MemoService memoService;

    @PutMapping("/memo")
    public ResponseEntity<ResponseDto> createMemo(@RequestBody CreateMemoDto createMemoDto) {
        try {
            CreateMemoDto.Res res = memoService.create(createMemoDto);
            return ResponseEntity.status(BaseResponseStatus.SUCCESS.getHttpStatus())
                    .body(new ResponseDto<>(BaseResponseStatus.SUCCESS, res));
        } catch (BaseException exception) {
            return ResponseEntity.status(exception.getBaseResponseStatus().getHttpStatus().value())
                    .body(new ResponseDto<>(exception.getBaseResponseStatus()));
        }
    }

}
