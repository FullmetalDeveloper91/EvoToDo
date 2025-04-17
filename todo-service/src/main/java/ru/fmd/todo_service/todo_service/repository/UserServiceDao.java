package ru.fmd.todo_service.todo_service.repository;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.fmd.todo_service.todo_service.model.User;


@Component
public class UserServiceDao {
    @Value("${user-service.base_url}")
    private String BASE_URL;
    private final String BEARER_PREFIX = "Bearer ";

    private final RestTemplate restTemplate;

    public UserServiceDao(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String login(User user){
        return restTemplate.postForObject(BASE_URL+"/login", user, String.class);
    }

    public ResponseEntity<User> getUserByLogin(String login, String token){
        return getWithToken(BASE_URL+"/{login}", login, token);
    }

    private ResponseEntity<User> getWithToken(
            String urlWithPathParam,
            String pathParameter,
            String token){
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", BEARER_PREFIX+token);

        HttpEntity<User> request = new HttpEntity<>(headers);

        return restTemplate.exchange(urlWithPathParam, HttpMethod.GET, request, User.class , pathParameter);
    }

}
