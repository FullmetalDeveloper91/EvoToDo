package ru.fmd.task_service.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import ru.fmd.task_service.model.TaskErrorEx;

@ControllerAdvice
public class taskExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<TaskErrorEx> notFoundException(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TaskErrorEx(HttpStatus.NOT_FOUND, ex.getMessage()));
    }
}
