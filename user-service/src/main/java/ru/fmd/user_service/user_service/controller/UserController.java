package ru.fmd.user_service.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.fmd.user_service.user_service.model.User;
import ru.fmd.user_service.user_service.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<User> findAll(){
        return userService.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody User user) throws UsernameNotFoundException {
        return ResponseEntity.ok(userService.signUpUser(user));
    }
}
