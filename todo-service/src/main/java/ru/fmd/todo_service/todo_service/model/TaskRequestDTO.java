package ru.fmd.todo_service.todo_service.model;

public class TaskRequestDTO {
    private String user;
    private String description;
    private Status status;

    public TaskRequestDTO() {
    }

    public TaskRequestDTO(String user, String description, Status status) {
        this.user = user;
        this.description = description;
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
