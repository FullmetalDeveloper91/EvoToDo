package ru.fmd.task_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.fmd.task_service.model.Status;
import ru.fmd.task_service.model.Task;
import ru.fmd.task_service.repository.TasksRepository;

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

    public List<Task> getByLogin(String login) {
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
    public Task update(Long id, String description, String ownerLogin) throws AuthorizationDeniedException {
        var task = tasksRepository.findById(id).orElseThrow(() -> notFoundExceptionMethod(id));
        if(!task.getLogin().equals(ownerLogin))
            throw new AuthorizationDeniedException("Only owner has the right to change the tasks");
        task.setDescription(description);
        return tasksRepository.save(task);
    }

    @Transactional
    public Task closeTask(Long id, String login) throws AuthorizationDeniedException, ResponseStatusException {
        Task task = tasksRepository.findById(id).orElseThrow(() -> notFoundExceptionMethod(id));

        if(!task.getLogin().equals(login))
            throw new AuthorizationDeniedException("Only owner has the right to close the task");

        task.setSuccessAt(LocalDateTime.now());
        task.setStatus(Status.SUCCESS);

        return tasksRepository.save(task);
    }

    public Task delete(Long id, String ownerLogin) throws AuthorizationDeniedException, ResponseStatusException {
        Task task = tasksRepository.findById(id).orElseThrow(() -> notFoundExceptionMethod(id));
        if(!task.getLogin().equals(ownerLogin))
            throw new AuthorizationDeniedException("Only owner has the right to delete the task");
        tasksRepository.delete(task);
        return task;
    }

    public List<Task> getActualTasks(LocalDateTime from, LocalDateTime to, String login) {
        from = from==null?LocalDateTime.of(1970,1,1,0,0):from;
        to = to==null?LocalDateTime.now():to;

        return tasksRepository.findActualTasks(login, Status.CREATED, from, to);
    }

    public List<Task> getActualTasks(LocalDateTime from, LocalDateTime to) {
        from = from==null?LocalDateTime.of(1970,1,1,0,0):from;
        to = to==null?LocalDateTime.now():to;

        return tasksRepository.findActualTasks(Status.CREATED, from, to);
    }

    private ResponseStatusException notFoundExceptionMethod(Long id){
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id `%s` not found".formatted(id));
    }
}
