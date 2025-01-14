package com.goblin.goandblinblog.global.exception;

import com.goblin.goandblinblog.global.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GoAndBlinException.class)
    public ResponseEntity<ErrorResponse> expirationDateException(GoAndBlinException e) {
        int status = e.getErrorCode().getStatus();

        ErrorResponse response = ErrorResponse.builder()
            .status(status)
            .message(e.getErrorCode().getMessage())
            .validation(e.getValidation())
            .build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalidRequestHandler(MethodArgumentNotValidException e) {
        int status = e.getStatusCode().value();

        ErrorResponse response = ErrorResponse.builder().status(status)
            .message(e.getMessage())
            .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> argsTypeMismatch(MethodArgumentTypeMismatchException e) {
        int status = HttpStatus.BAD_REQUEST.value();
        String message = e.getParameter().getParameterName() + "은 올바르지 않은 값입니다.";

        ErrorResponse response = ErrorResponse.builder().status(status)
            .message(message)
            .build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> missingRequestParam(MissingServletRequestParameterException e) {
        int status = HttpStatus.BAD_REQUEST.value();
        String message = "공백은 허용되지 않습니다.";

        ErrorResponse response = ErrorResponse.builder().status(status)
            .message(message)
            .build();

        return ResponseEntity.status(status).body(response);
    }

}