package ru.fmd.user_service.model;

import org.springframework.http.HttpStatus;

public class UserErrorEx {
    private HttpStatus status;
    private String msg;

    public UserErrorEx() {
    }

    public UserErrorEx(HttpStatus status, String msg) {
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
