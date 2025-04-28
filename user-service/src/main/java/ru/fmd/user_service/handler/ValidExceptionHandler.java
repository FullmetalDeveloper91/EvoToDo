package ru.fmd.user_service.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.fmd.user_service.model.UserErrorEx;

@ControllerAdvice
public class ValidExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<UserErrorEx> parseException(RuntimeException ex){
        return ResponseEntity.badRequest().body(new UserErrorEx(HttpStatus.BAD_REQUEST,ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserErrorEx> validException(MethodArgumentNotValidException ex){
        return ResponseEntity.badRequest().body(new UserErrorEx(HttpStatus.BAD_REQUEST,ex.getMessage()));
    }
}
