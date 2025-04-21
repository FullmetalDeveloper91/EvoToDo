package ru.fmd.todo_service.todo_service.service;

import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.fmd.todo_service.todo_service.model.Status;
import ru.fmd.todo_service.todo_service.model.Task;
import ru.fmd.todo_service.todo_service.model.User;
import ru.fmd.todo_service.todo_service.repository.TasksRepository;
import ru.fmd.todo_service.todo_service.repository.UserServiceDao;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    @Value("${user-service.base_url}")
    private String BASE_URL;

    private final TasksRepository tasksRepository;

    public TaskService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public List<Task> getAll() {
        return tasksRepository.findAll();
    }

    public List<Task> getAll(String login) {
        return tasksRepository.findByLogin(login);
    }

    public Task create(String login, String description) {
        Task task = new Task(login, description);
        return tasksRepository.save(task);
    }

    public Task getOne(Long id) {
        return tasksRepository.findById(id).orElseThrow(() -> notFoundExceptionMethod(id));
    }

    @Transactional
    public Task update(Long id, String description) {
        var task = tasksRepository.findById(id).orElseThrow(() -> notFoundExceptionMethod(id));
        task.setDescription(description);
        return tasksRepository.save(task);
    }

    @Transactional
    public Task closeTask(Long id){
        Task task = tasksRepository.findById(id).orElseThrow(() -> notFoundExceptionMethod(id));
        task.setSuccessAt(LocalDateTime.now());
        task.setStatus(Status.SUCCESS);
        return tasksRepository.save(task);
    }

    @Transactional
    public Task closeTask(Long id, String login) {
        Task task = tasksRepository.findById(id).orElseThrow(() -> notFoundExceptionMethod(id));

        if(!task.getLogin().equals(login))
            throw new AuthorizationDeniedException("The administrator or the owner has the right to close the task");

        task.setSuccessAt(LocalDateTime.now());
        task.setStatus(Status.SUCCESS);

        return tasksRepository.save(task);
    }

    public Task delete(Long id) {
        Task task = tasksRepository.findById(id).orElseThrow(() -> notFoundExceptionMethod(id));
        tasksRepository.delete(task);
        return task;
    }

    private ResponseStatusException notFoundExceptionMethod(Long id){
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id `%s` not found".formatted(id));
    }
}
