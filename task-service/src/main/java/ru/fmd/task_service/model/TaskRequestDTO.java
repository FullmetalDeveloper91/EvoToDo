package ru.fmd.task_service.model;

public class TaskRequestDTO {
    private String description;

    public TaskRequestDTO() {
    }

    public TaskRequestDTO( String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
