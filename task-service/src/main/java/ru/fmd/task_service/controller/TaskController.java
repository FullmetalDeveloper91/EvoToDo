package ru.fmd.task_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.fmd.task_service.model.Role;
import ru.fmd.task_service.model.Task;
import ru.fmd.task_service.model.TaskRequestDTO;
import ru.fmd.task_service.model.UserAction;
import ru.fmd.task_service.repository.TaskLogDao;
import ru.fmd.task_service.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = {"/api/v1/task"})
public class TaskController {

    private final TaskService taskService;
    private final TaskLogDao logDao;

    public TaskController(TaskService taskService, TaskLogDao logDao) {
        this.taskService = taskService;
        this.logDao = logDao;
    }

    @GetMapping()
    public List<Task> getAllTasks(SecurityContextHolderAwareRequestWrapper securityContext) {
        return securityContext.isUserInRole(Role.ADMIN.name())
            ? taskService.getAll()
            : taskService.getByLogin(securityContext.getRemoteUser());
    }

    @GetMapping("/actual")
    public List<Task> getActualTasks(
            @RequestParam(required = false, name="date_from") LocalDateTime from,
            @RequestParam(required = false, name="date_to") LocalDateTime to,
            SecurityContextHolderAwareRequestWrapper securityContext){
        return securityContext.isUserInRole(Role.ADMIN.name())
                ? taskService.getActualTasks(from, to)
                : taskService.getActualTasks(from, to, securityContext.getRemoteUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<Task> create(
            @RequestBody TaskRequestDTO taskReq,
            SecurityContextHolderAwareRequestWrapper securityContext) {
        //var newTask = taskService.create(securityContext.getRemoteUser(),taskReq.getDescription());
        //logDao.writeLog(UserAction.CREATE_TASK, "User create task with id %d".formatted(newTask.getId()),securityContext.getHeader("Authorization"));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.create(securityContext.getRemoteUser(),taskReq.getDescription()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(
            @PathVariable Long id,
            @RequestBody TaskRequestDTO taskReq,
            SecurityContextHolderAwareRequestWrapper securityContext) {
        try{
            var updatedTask = taskService.update(
                    id,
                    taskReq.getDescription(),
                    securityContext.getRemoteUser());
            logDao.writeLog(UserAction.CHANGE_TASK, "User change task with id %d".formatted(updatedTask.getId()),securityContext.getHeader("Authorization"));
            return ResponseEntity.ok(updatedTask);
        }catch (AuthorizationDeniedException ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    //TODO Исправить тело метода на стратегию или добавить ExceptionHandler
    @PutMapping("/{id}/close")
    public ResponseEntity<Task> closeTask(
            @PathVariable Long id,
            SecurityContextHolderAwareRequestWrapper securityContext){
        try{
            var closedTask = taskService.closeTask(id, securityContext.getRemoteUser());
            logDao.writeLog(UserAction.CLOSING_TASK, "User close task with id %d".formatted(closedTask.getId()),securityContext.getHeader("Authorization"));
            return ResponseEntity.ok(closedTask);
        }catch (AuthorizationDeniedException ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (ResponseStatusException ex){
            return  ResponseEntity.status(ex.getStatusCode()).build();
        }
    }

    //TODO Исправить тело метода на стратегию или добавить ExceptionHandler
    @DeleteMapping("/{id}")
    public ResponseEntity<Task> delete(
            @PathVariable Long id,
            SecurityContextHolderAwareRequestWrapper securityContext) {
        try{
            var deletedTask = taskService.delete(id, securityContext.getRemoteUser());
            logDao.writeLog(UserAction.DELETE_TASK, "User delete task \"%s\"".formatted(deletedTask.getDescription()),securityContext.getHeader("Authorization"));
            return ResponseEntity.ok(deletedTask);
        }catch (AuthorizationDeniedException ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (ResponseStatusException ex){
            return  ResponseEntity.status(ex.getStatusCode()).build();
        }
    }
}