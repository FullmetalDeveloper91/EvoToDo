package ru.fmd.log_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class TaskLog {
    @Id @GeneratedValue
    private Long id;
    private String login;
    private LocalDateTime createdAt;
    private UserAction action;

    public TaskLog() {
    }

    public TaskLog(String login, LocalDateTime createdAt, UserAction action) {
        this.login = login;
        this.createdAt = createdAt;
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserAction getAction() {
        return action;
    }

    public void setAction(UserAction action) {
        this.action = action;
    }
}
