package dev.gooiman.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends Exception{
    private BaseResponseStatus baseResponseStatus;
}
