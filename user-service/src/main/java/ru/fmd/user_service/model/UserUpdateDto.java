package ru.fmd.user_service.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class UserUpdateDto {
    @Min(6) @Max(30)
    private String fio;
    private Role role;

    public UserUpdateDto() {
    }

    public UserUpdateDto(String fio, Role role) {
        this.fio = fio;
        this.role = role;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    //TODO Сделать шифрование пароля
    public User mapToUser(User user){
        if(fio != null)
            if(!(fio.isEmpty() && user.getFio().equals(fio)))
                user.setFio(fio);
        if (role != null && user.getRole() != role)
            user.setRole(role);

        return user;
    }
}