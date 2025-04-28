package ru.fmd.user_service.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import ru.fmd.user_service.model.Role;
import ru.fmd.user_service.model.User;

public class UserUpdateDto {
    @Length(min = 5, max = 100, message = "Length must be between 5 and 100")
    @NotBlank
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

    public User mapToUser(User user){
        if(fio != null)
            if(!(fio.isEmpty() && user.getFio().equals(fio)))
                user.setFio(fio);
        if (role != null && user.getRole() != role)
            user.setRole(role);

        return user;
    }
}