package ru.fmd.log_service.model;

public class TaskLogDto {
    private UserAction action;

    public TaskLogDto(UserAction action) {
        this.action = action;
    }

    public UserAction getAction() {
        return action;
    }
}