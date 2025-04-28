package ru.fmd.task_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
public class Task {
    @Id @GeneratedValue
    private Long id;
    @Length(min = 3, max = 50, message = "Length must be between 3 and 50")
    @NotBlank
    private String login;
    @Length(min = 10, max = 200, message = "Length must be between 10 and 200")
    @NotBlank
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime successAt;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Task() {
    }

    public Task(String login, String description) {
        this.login = login;
        this.description = description;
        this.status = Status.CREATED;
        this.createdAt = LocalDateTime.now();
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