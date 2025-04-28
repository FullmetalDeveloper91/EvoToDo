package ru.fmd.task_service.model;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class TaskRequestDTO {
    @Length(min = 10, max = 200, message = "Length must be between 10 and 200")
    @NotBlank
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
