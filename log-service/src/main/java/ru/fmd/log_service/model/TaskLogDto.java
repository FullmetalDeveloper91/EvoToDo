package ru.fmd.log_service.model;

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

    public TaskLog updateTaskLog(TaskLog taskLog){
        if(action != null && taskLog.getAction() != action)
            taskLog.setAction(action);

        if(!description.isBlank() && !taskLog.getDescription().equals(description))
            taskLog.setDescription(description);

        return taskLog;
    }
}