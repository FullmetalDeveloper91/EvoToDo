package ru.fmd.user_service.handler;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.fmd.user_service.model.UserErrorEx;

@ControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler({
            AuthorizationDeniedException.class,
            UsernameNotFoundException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<UserErrorEx> authException(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new UserErrorEx(HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<UserErrorEx> jwtExpiredException(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new UserErrorEx(HttpStatus.FORBIDDEN,
            "JWT is expired. Please, login again.")
        );
    }
}
