package ru.fmd.todo_service.todo_service.controller;

import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public List<Task> getAll(SecurityContextHolderAwareRequestWrapper securityContext) {
        return securityContext.isUserInRole("ADMIN")
            ? taskService.getAll()
            : taskService.getAll(securityContext.getRemoteUser());
    }

    @GetMapping("/{id}")
    public Task getOne(@PathVariable Long id) {
        return taskService.getOne(id);
    }

    @PostMapping
    public Task create(@RequestBody TaskRequestDTO taskReq, SecurityContextHolderAwareRequestWrapper securityContext) {
        return taskService.create(securityContext.getRemoteUser(),taskReq.getDescription());
    }

    //TODO Добавить секьюрность. Изменять таски может админ или оунер
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

    //TODO Добавить секьюрность. Удалять таски может админ или оунер
    @DeleteMapping("/{id}")
    public Task delete(@PathVariable Long id) {
        return taskService.delete(id);
    }
}