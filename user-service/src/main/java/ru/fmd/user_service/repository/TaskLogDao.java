package ru.fmd.user_service.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.fmd.user_service.model.TaskLog;
import ru.fmd.user_service.model.TaskLogDto;
import ru.fmd.user_service.model.UserAction;

@Component
public class TaskLogDao {

    @Value("${log-service.base_url}")
    private String BASE_URL;
    private final String BEARER_PREFIX = "Bearer ";

    private final RestTemplate restTemplate;

    public TaskLogDao(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<TaskLog> writeLog(UserAction action, String description, String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", BEARER_PREFIX+token);

        HttpEntity<TaskLogDto> request = new HttpEntity<>(new TaskLogDto(action, description), headers);

        return restTemplate.exchange(BASE_URL, HttpMethod.POST, request, TaskLog.class);
    }
}
