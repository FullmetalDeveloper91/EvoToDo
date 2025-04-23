package ru.fmd.log_service.service;

import org.springframework.stereotype.Service;
import ru.fmd.log_service.model.TaskLog;
import ru.fmd.log_service.model.TaskLogDto;
import ru.fmd.log_service.repository.TaskLogRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskLogService {

    private final TaskLogRepository repository;

    public TaskLogService(TaskLogRepository repository) {
        this.repository = repository;
    }

    public List<TaskLog> findAll() {
        return repository.findAll();
    }

    public TaskLog create(String login, TaskLogDto logDto){
        var taskLog = new TaskLog(login, LocalDateTime.now(), logDto.getAction(), logDto.getDescription());
        return repository.save(taskLog);
    }
}
