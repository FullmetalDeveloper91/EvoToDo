package ru.fmd.todo_service.todo_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;
import ru.fmd.todo_service.todo_service.model.Task;
import ru.fmd.todo_service.todo_service.model.TaskRequestDTO;
import ru.fmd.todo_service.todo_service.model.User;
import ru.fmd.todo_service.todo_service.service.TaskService;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/v1/task"})
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAll(SecurityContextHolderAwareRequestWrapper securityContext) {
        if(securityContext.isUserInRole("ADMIN"))
            return taskService.getAll();
        else
            return taskService.getAll(securityContext.getRemoteUser());
    }

    @GetMapping("/{id}")
    public Task getOne(@PathVariable Long id) {
        return taskService.getOne(id);
    }

    @GetMapping("/user/{login}")
    public ResponseEntity<User> getUserById(@PathVariable String login, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        return taskService.getUserByLogin(login, token);
    }

    @PostMapping
    public Task create(@RequestBody TaskRequestDTO taskReq, SecurityContextHolderAwareRequestWrapper securityContext) {
        return taskService.create(taskReq.getDescription(), securityContext.getRemoteUser());
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody TaskRequestDTO taskReq) {
        return taskService.update(id, taskReq.getDescription());
    }

    @PutMapping("/{id}/close")
    public Task closeTask(@PathVariable Long id, SecurityContextHolderAwareRequestWrapper securityContext){
        if(securityContext.isUserInRole("ADMIN"))
            return taskService.closeTask(id);
        else
            return taskService.closeTask(id, securityContext.getRemoteUser());
    }

    @DeleteMapping("/{id}")
    public Task delete(@PathVariable Long id) {
        return taskService.delete(id);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return taskService.login(user);
    }
}