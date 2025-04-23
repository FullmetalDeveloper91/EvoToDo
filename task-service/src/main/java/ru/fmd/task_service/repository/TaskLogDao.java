package ru.fmd.task_service.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.fmd.task_service.model.TaskLog;
import ru.fmd.task_service.model.TaskLogDto;
import ru.fmd.task_service.model.UserAction;

@Component
public class TaskLogDao {

    @Value("${log-service.base_url}")
    private String BASE_URL;

    private final RestTemplate restTemplate;

    public TaskLogDao(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<TaskLog> writeLog(UserAction action, String description, String authString){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authString);

        HttpEntity<TaskLogDto> request = new HttpEntity<>(new TaskLogDto(action, description), headers);

        return restTemplate.exchange(BASE_URL, HttpMethod.POST, request, TaskLog.class);
    }
}
