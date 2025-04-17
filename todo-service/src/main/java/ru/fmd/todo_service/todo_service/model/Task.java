package ru.fmd.todo_service.todo_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Task {
    @Id @GeneratedValue
    private Long id;
    private String login;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime successAt;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Task() {
    }

    public Task(String login, String description, Status status) {
        this.login = login;
        this.description = description;
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getSuccessAt() {
        return successAt;
    }

    public void setSuccessAt(LocalDateTime successAt) {
        this.successAt = successAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}