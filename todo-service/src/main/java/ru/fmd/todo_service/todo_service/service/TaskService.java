package ru.fmd.todo_service.todo_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import ru.fmd.todo_service.todo_service.model.Task;
import ru.fmd.todo_service.todo_service.model.User;
import ru.fmd.todo_service.todo_service.repository.tasksRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Value("${user-service.base_url}")
    private String BASE_URL;

    private final tasksRepository tasksRepository;
    private final RestTemplate restTemplate;

    public TaskService(tasksRepository tasksRepository, RestTemplate restTemplate) {
        this.tasksRepository = tasksRepository;
        this.restTemplate = restTemplate;
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

    public String login(User user){
        return restTemplate.postForObject(BASE_URL+"/login", user, String.class);
    }
}
