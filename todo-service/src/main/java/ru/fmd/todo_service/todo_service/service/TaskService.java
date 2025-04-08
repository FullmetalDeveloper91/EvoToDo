package ru.fmd.todo_service.todo_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.fmd.todo_service.todo_service.model.Task;
import ru.fmd.todo_service.todo_service.repository.tasksRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final tasksRepository tasksRepository;

    public TaskService(tasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public List<Task> getAll() {
        return tasksRepository.findAll();
    }

    public Task getOne(Long id) {
        Optional<Task> taskOptional = tasksRepository.findById(id);
        return taskOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    public Task create(Task task) {
        return tasksRepository.save(task);
    }

    public Task update(Long id, Task task) {
        if (!tasksRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id));
        }
        return tasksRepository.save(task);
    }

    public Task delete(Long id) {
        Task task = tasksRepository.findById(id).orElse(null);
        if (task != null) {
            tasksRepository.delete(task);
        }
        return task;
    }
}
