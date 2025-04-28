package ru.fmd.task_service.model;

import org.springframework.http.HttpStatus;

public class TaskErrorEx {
    private HttpStatus status;
    private String msg;

    public TaskErrorEx() {
    }

    public TaskErrorEx(HttpStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
