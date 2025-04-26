package ru.fmd.log_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;
import ru.fmd.log_service.model.TaskLog;
import ru.fmd.log_service.model.TaskLogDto;
import ru.fmd.log_service.service.TaskLogService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/log")
public class TaskLogController {
    private final TaskLogService taskLogService;

    public TaskLogController(TaskLogService taskLogService) {
        this.taskLogService = taskLogService;
    }

    @GetMapping
    public List<TaskLog> findAll(
            @RequestParam(name = "date_from", required = false )LocalDateTime dateTimeFrom,
            @RequestParam(name = "date_to", required = false ) LocalDateTime dateTimeTo){
        return taskLogService.findAll(dateTimeFrom, dateTimeTo);
    }

    @GetMapping("/{login}")
    public List<TaskLog> findByLogin(
            @PathVariable String login,
            @RequestParam(name = "date_from", required = false )LocalDateTime dateTimeFrom,
            @RequestParam(name = "date_to", required = false ) LocalDateTime dateTimeTo){
        return taskLogService.findByLogin(login, dateTimeFrom, dateTimeTo);
    }

    @PostMapping
    public ResponseEntity<TaskLog> create(
            @RequestBody TaskLogDto taskLogDto,
            SecurityContextHolderAwareRequestWrapper securityContext){
        return ResponseEntity.ok(taskLogService.create(securityContext.getRemoteUser(), taskLogDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskLog> update(@PathVariable Long id, @RequestBody TaskLogDto taskLogDto){
        return ResponseEntity.ok(taskLogService.update(id, taskLogDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskLog> delete(@PathVariable Long id){
        return ResponseEntity.ok(taskLogService.delete(id));
    }
}
