package ru.fmd.todo_service.todo_service.controller;

import org.springframework.web.bind.annotation.*;
import ru.fmd.todo_service.todo_service.model.Task;
import ru.fmd.todo_service.todo_service.service.TaskService;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/v1/tasks"})
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
}