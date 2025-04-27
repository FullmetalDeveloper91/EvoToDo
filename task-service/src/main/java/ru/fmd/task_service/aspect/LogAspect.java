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
    public void isControllerLayer(){
    }

    @Pointcut("isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping(){
    }

    @Pointcut("execution(public * ru.fmd.task_service.controller.*Controller.create(..))")
    public void isCreateTaskMethod(){

    }

    @AfterReturning(value = "isControllerLayer()" +
            "&&isCreateTaskMethod()" +
            "&& args(*, securityContext)",
        returning = "responseWithTask")
    public void addLogging(ResponseEntity<Task> responseWithTask, SecurityContextHolderAwareRequestWrapper securityContext){
        var newTask = responseWithTask.getBody();
        if(newTask!=null)
            logDao.writeLog(
                    UserAction.CREATE_TASK,
                    "User create task with id %d".formatted(newTask.getId()),
                    securityContext.getHeader("Authorization")
            );
    }
} 