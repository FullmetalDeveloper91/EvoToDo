package ru.fmd.task_service.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;
import ru.fmd.task_service.model.Task;
import ru.fmd.task_service.model.UserAction;
import ru.fmd.task_service.repository.TaskLogDao;

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
            returning = "responseWithTask")
    public void addLogging(
            ToLog toLog,
            ResponseEntity<Task> responseWithTask,
            SecurityContextHolderAwareRequestWrapper securityContext){

        var newTask = responseWithTask.getBody();
        String message = "";

        if(newTask != null) {
            switch (toLog.action()) {
                case CREATE_TASK -> message = "User %s create task with id %d"
                                        .formatted(securityContext.getRemoteUser(), newTask.getId());
                case CHANGE_TASK -> message = "User %s changed task with id %d"
                                        .formatted(securityContext.getRemoteUser(), newTask.getId());
                case CLOSING_TASK -> message = "User %s close task with id %d"
                                        .formatted(securityContext.getRemoteUser(), newTask.getId());
                case DELETE_TASK -> message = "User %s delete task with id %d"
                                        .formatted(securityContext.getRemoteUser(), newTask.getId());
            }

            logDao.writeLog(
                    toLog.action(),
                    message,
                    securityContext.getHeader("Authorization")
            );
        }
    }
}