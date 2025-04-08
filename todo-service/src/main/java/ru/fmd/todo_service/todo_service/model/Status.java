package ru.fmd.todo_service.todo_service.model;

public enum Status {
    CREATED("created"),
    ATTACHED("attached"),
    SUCCESS("success");

    private final String title;

    Status(String title) {
        this.title = title;
    }

    @Override
    public String toString(){
        return this.title;
    }
}
