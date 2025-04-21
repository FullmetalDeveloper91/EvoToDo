package ru.fmd.user_service.user_service.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;
import ru.fmd.user_service.user_service.model.Role;
import ru.fmd.user_service.user_service.model.User;
import ru.fmd.user_service.user_service.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{login}")
    public ResponseEntity<User> findByLogin(@PathVariable String login){
        var user = userService.findByLogin(login);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        try {
            return ResponseEntity.ok(userService.registerUser(user));
        }catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody User user) {
        try{
            return ResponseEntity.ok(userService.signUpUser(user));
        }
        catch (UsernameNotFoundException ex){
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{login}")
    public ResponseEntity<User> update(
            @RequestBody User user,
            @PathVariable String login,
            SecurityContextHolderAwareRequestWrapper securityContext){

        if(securityContext.isUserInRole(Role.ADMIN.name()) || login.equals(securityContext.getRemoteUser())){
            Pair<Boolean, Optional<User>> updatedUserPair = userService.update(user);
            return updatedUserPair.getFirst()
                    ? ResponseEntity.ok(user)
                    : ResponseEntity.status(HttpStatus.CREATED).body(user);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<User> delete(
            @PathVariable String login,
            SecurityContextHolderAwareRequestWrapper securityContext){
        if(securityContext.isUserInRole(Role.ADMIN.name()) || login.equals(securityContext.getRemoteUser())){
            return userService.delete(login);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
