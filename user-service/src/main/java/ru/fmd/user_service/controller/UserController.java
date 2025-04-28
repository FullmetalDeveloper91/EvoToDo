package ru.fmd.user_service.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.fmd.user_service.aspect.ToLog;
import ru.fmd.user_service.model.*;
import ru.fmd.user_service.model.dto.NewUserDto;
import ru.fmd.user_service.model.dto.UserLoginDto;
import ru.fmd.user_service.model.dto.UserUpdateDto;
import ru.fmd.user_service.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{login}")
    public ResponseEntity<User> findByLogin(
            @PathVariable String login,
            SecurityContextHolderAwareRequestWrapper securityContext){
        return userService.findByLogin(login).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{login}")
    public ResponseEntity<User> update(
            @PathVariable String login,
            SecurityContextHolderAwareRequestWrapper securityContext,
            @RequestBody @Validated UserUpdateDto user){
        return ResponseEntity.ok(userService.update(login, user));
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<User> delete(
            @PathVariable String login,
            SecurityContextHolderAwareRequestWrapper securityContext){
        userService.delete(login);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Validated NewUserDto user){
        return ResponseEntity.ok(userService.registerUser(
                new User(user.getLogin(), user.getPassword(), user.getFio(), user.getRole())));
    }

    @PostMapping("/login")
    @ToLog(action = UserAction.AUTHORIZATION)
    public ResponseEntity<String> login (@RequestBody @Validated UserLoginDto userLoginDto) {
        return ResponseEntity.ok(userService.signUpUser(userLoginDto.toUser()));
    }
}