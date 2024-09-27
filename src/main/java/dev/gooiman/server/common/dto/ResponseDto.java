package dev.gooiman.server.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.gooiman.server.common.exception.BaseResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private Integer statusVal;
    private String message;
    private HttpStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public ResponseDto(BaseResponseStatus status, T result) {
        this.statusVal = status.getHttpStatus().value();
        this.status = status.getHttpStatus();
        this.message = status.getMessage();
        this.result = result;
    }

    public ResponseDto(BaseResponseStatus status) {
        this.statusVal = status.getHttpStatus().value();
        this.status = status.getHttpStatus();
        this.message = status.getMessage();
    }
}
