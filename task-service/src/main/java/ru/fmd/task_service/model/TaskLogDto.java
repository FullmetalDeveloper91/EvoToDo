package ru.fmd.task_service.model;

public class TaskLogDto {
    private UserAction action;
    private String description;

    public TaskLogDto(UserAction action, String description) {
        this.action = action;
        this.description = description;
    }

    public UserAction getAction() {
        return action;
    }

    public String getDescription() {
        return description;
    }
}