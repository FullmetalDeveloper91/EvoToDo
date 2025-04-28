package ru.fmd.task_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.fmd.task_service.aspect.ToLog;
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

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
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
    @ToLog(action = UserAction.CREATE_TASK)
    public ResponseEntity<Task> create(
            @RequestBody @Validated TaskRequestDTO taskReq,
            SecurityContextHolderAwareRequestWrapper securityContext) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.create(securityContext.getRemoteUser(),taskReq.getDescription()));
    }

    @PutMapping("/{id}")
    @ToLog(action = UserAction.CHANGE_TASK)
    public ResponseEntity<Task> update(
            @PathVariable Long id,
            @RequestBody @Validated TaskRequestDTO taskReq,
            SecurityContextHolderAwareRequestWrapper securityContext) {
        return ResponseEntity.ok(taskService.update(
                id,
                taskReq.getDescription(),
                securityContext.getRemoteUser()));
    }

    @PutMapping("/{id}/close")
    @ToLog(action = UserAction.CLOSING_TASK)
    public ResponseEntity<Task> closeTask(
            @PathVariable Long id,
            SecurityContextHolderAwareRequestWrapper securityContext){
        return ResponseEntity.ok(taskService.closeTask(id, securityContext.getRemoteUser()));
    }

    @DeleteMapping("/{id}")
    @ToLog(action = UserAction.DELETE_TASK)
    public ResponseEntity<Task> delete(
            @PathVariable Long id,
            SecurityContextHolderAwareRequestWrapper securityContext) {
        return ResponseEntity.ok(taskService.delete(id, securityContext.getRemoteUser()));
    }
}