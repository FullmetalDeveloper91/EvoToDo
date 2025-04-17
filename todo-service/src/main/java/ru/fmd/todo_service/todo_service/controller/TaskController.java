package ru.fmd.todo_service.todo_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmd.todo_service.todo_service.model.Task;
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
    public List<Task> getAll() {
        return taskService.getAll();
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
    public Task create(@RequestBody Task task) {
        return taskService.create(task);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task) {
        return taskService.update(id, task);
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