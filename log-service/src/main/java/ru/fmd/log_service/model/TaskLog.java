package ru.fmd.log_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TaskLog {
    @Id @GeneratedValue
    private Long id;
    private String login;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private UserAction action;
    private String description;

    public TaskLog() {
    }

    public TaskLog(String login, LocalDateTime createdAt, UserAction action, String description) {
        this.login = login;
        this.createdAt = createdAt;
        this.action = action;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
