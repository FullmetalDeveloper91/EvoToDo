package ru.fmd.task_service.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.fmd.task_service.model.TaskErrorEx;

@ControllerAdvice
public class ValidExceptionHandler {
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<TaskErrorEx> parseException(RuntimeException ex){
        return ResponseEntity.badRequest().body(new TaskErrorEx(HttpStatus.BAD_REQUEST,ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TaskErrorEx> validException(MethodArgumentNotValidException ex){
        return ResponseEntity.badRequest().body(new TaskErrorEx(HttpStatus.BAD_REQUEST,ex.getMessage()));
    }
}
