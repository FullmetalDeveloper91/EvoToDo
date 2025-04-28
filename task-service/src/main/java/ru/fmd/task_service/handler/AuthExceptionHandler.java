package ru.fmd.task_service.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.fmd.task_service.model.TaskErrorEx;
import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<TaskErrorEx> authException(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new TaskErrorEx(HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<TaskErrorEx> jwtExpiredException(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new TaskErrorEx(HttpStatus.FORBIDDEN,
            "JWT is expired. Please, login again.")
        );
    }
}
