package ru.fmd.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;
import ru.fmd.user_service.model.*;
import ru.fmd.user_service.repository.TaskLogDao;
import ru.fmd.user_service.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final TaskLogDao logDao;

    public UserController(UserService userService, TaskLogDao logDao) {
        this.userService = userService;
        this.logDao = logDao;
    }

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{login}")
    public ResponseEntity<User> findByLogin(
            @PathVariable String login,
            SecurityContextHolderAwareRequestWrapper securityContext){
        return securityContext.isUserInRole(Role.ADMIN.name()) || login.equals(securityContext.getRemoteUser())
            ? userService.findByLogin(login).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build())
            : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/{login}")
    public ResponseEntity<User> update(
            @RequestBody UserUpdateDto user,
            @PathVariable String login,
            SecurityContextHolderAwareRequestWrapper securityContext){
        try{
            return securityContext.isUserInRole(Role.ADMIN.name()) || login.equals(securityContext.getRemoteUser())
                    ? ResponseEntity.ok(userService.update(login, user))
                    : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (UsernameNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<User> delete(
            @PathVariable String login,
            SecurityContextHolderAwareRequestWrapper securityContext){

        try {
            if(securityContext.isUserInRole(Role.ADMIN.name()) || login.equals(securityContext.getRemoteUser())){
                userService.delete(login);
                return ResponseEntity.ok().build();
            }
        }catch (UsernameNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody NewUserDto user){
        try {
            return ResponseEntity.ok(userService.registerUser(
                    new User(user.getLogin(), user.getPassword(), user.getFio(), user.getRole())));
        }catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody User user) {
        try{
            var token = userService.signUpUser(user);
            logDao.writeLog(UserAction.AUTHORIZATION, "The user %s is logged in".formatted(user.getLogin()), token);
            return ResponseEntity.ok(token);
        }
        catch (UsernameNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }
}
