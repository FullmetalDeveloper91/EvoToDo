package ru.fmd.todo_service.todo_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Task {
    @Id @GeneratedValue
    private Long id;
    private String user;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime successAt;
    @Enumerated(EnumType.STRING)
    private Status status;
}
