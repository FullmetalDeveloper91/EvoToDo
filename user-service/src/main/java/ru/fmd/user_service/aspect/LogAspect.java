package ru.fmd.user_service.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;
import ru.fmd.user_service.repository.TaskLogDao;

@Aspect
@Component
public class LogAspect {
    private final TaskLogDao logDao;

    public LogAspect(TaskLogDao logDao) {
        this.logDao = logDao;
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void isControllerLayer(){}

    @AfterReturning(value = "isControllerLayer()" +
            "&&@annotation(toLog)" +
            "&&args(*, securityContext)",
            returning = "responseWithToken")
    public void addLogging(
            ToLog toLog,
            ResponseEntity<String> responseWithToken,
            SecurityContextHolderAwareRequestWrapper securityContext){

        var token = responseWithToken.getBody();

        logDao.writeLog(
                toLog.action(),
                "The user %s is logged in".formatted(securityContext.getRemoteUser()),
                token);
    }
}