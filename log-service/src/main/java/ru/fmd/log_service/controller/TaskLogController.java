package ru.fmd.log_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;
import ru.fmd.log_service.model.TaskLog;
import ru.fmd.log_service.model.TaskLogDto;
import ru.fmd.log_service.service.TaskLogService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/log")
public class TaskLogController {
    private final TaskLogService taskLogService;

    public TaskLogController(TaskLogService taskLogService) {
        this.taskLogService = taskLogService;
    }

    @GetMapping
    public List<TaskLog> findAll(){
        return taskLogService.findAll();
    }

    @PostMapping
    public ResponseEntity<TaskLog> createLog(
            @RequestBody TaskLogDto taskLogDto,
            SecurityContextHolderAwareRequestWrapper securityContext){
        return ResponseEntity.ok(taskLogService.create(securityContext.getRemoteUser(), taskLogDto));
    }
}
