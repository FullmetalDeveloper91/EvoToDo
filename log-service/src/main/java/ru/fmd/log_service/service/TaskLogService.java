package ru.fmd.log_service.service;

import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public List<TaskLog> findAll(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        dateTimeFrom = dateTimeFrom == null ? LocalDateTime.of(1970,1,1,0,0): dateTimeFrom;
        dateTimeTo = dateTimeTo == null ? LocalDateTime.now() : dateTimeTo;

        return repository.findLogs(dateTimeFrom, dateTimeTo);
    }

    public List<TaskLog> findByLogin(String login, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        dateTimeFrom = dateTimeFrom == null ? LocalDateTime.of(1970,1,1,0,0): dateTimeFrom;
        dateTimeTo = dateTimeTo == null ? LocalDateTime.now() : dateTimeTo;

        return repository.findLogsByLogin(login, dateTimeFrom, dateTimeTo);
    }

    public TaskLog create(String login, TaskLogDto logDto){
        var taskLog = new TaskLog(login, LocalDateTime.now(), logDto.getAction(), logDto.getDescription());
        return repository.save(taskLog);
    }

    @Transactional
    public TaskLog delete(Long id) {
        var taskLog = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Log with ID %d not found".formatted(id)));
        repository.delete(taskLog);
        return taskLog;
    }

    @Transactional
    public TaskLog update(Long id, TaskLogDto taskLogDto) {
        var taskLog = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Log with ID %d not found".formatted(id)));

        taskLog = taskLogDto.updateTaskLog(taskLog);
        return repository.save(taskLog);
    }
}
