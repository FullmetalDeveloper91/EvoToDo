package ru.fmd.todo_service.todo_service.controller;

import jakarta.ws.rs.QueryParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.fmd.todo_service.todo_service.model.Role;
import ru.fmd.todo_service.todo_service.model.Task;
import ru.fmd.todo_service.todo_service.model.TaskRequestDTO;
import ru.fmd.todo_service.todo_service.service.TaskService;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/v1/task"})
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasksByLogin(
            @QueryParam("login") String login,
            SecurityContextHolderAwareRequestWrapper securityContext){
        if(login.equals(securityContext.getRemoteUser()) || securityContext.isUserInRole(Role.ADMIN.name()))
            return ResponseEntity.ok(taskService.getByLogin(login));
        else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<Task> create(
            @RequestBody TaskRequestDTO taskReq,
            SecurityContextHolderAwareRequestWrapper securityContext) {
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
            return ResponseEntity.ok(taskService.update(
                    id,
                    taskReq.getDescription(),
                    securityContext.getRemoteUser()));
        }catch (AuthorizationDeniedException ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    //TODO Исправить тело метода на стратегию или добавить ExceptionHandler
    @PutMapping("/{id}/close")
    public ResponseEntity<Task> closeTask(@PathVariable Long id, SecurityContextHolderAwareRequestWrapper securityContext){
        try{
            return ResponseEntity.ok(taskService.closeTask(id, securityContext.getRemoteUser()));
        }catch (AuthorizationDeniedException ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (ResponseStatusException ex){
            return  ResponseEntity.status(ex.getStatusCode()).build();
        }
    }

    //TODO Исправить тело метода на стратегию или добавить ExceptionHandler
    @DeleteMapping("/{id}")
    public ResponseEntity<Task> delete(@PathVariable Long id, SecurityContextHolderAwareRequestWrapper securityContext) {
        try{
            return ResponseEntity.ok(taskService.delete(id, securityContext.getRemoteUser()));
        }catch (AuthorizationDeniedException ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (ResponseStatusException ex){
            return  ResponseEntity.status(ex.getStatusCode()).build();
        }
    }
}