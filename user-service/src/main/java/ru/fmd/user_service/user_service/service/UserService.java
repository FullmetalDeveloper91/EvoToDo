package ru.fmd.user_service.user_service.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fmd.user_service.user_service.model.User;
import ru.fmd.user_service.user_service.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    public Optional<User> findByLogin(String login){
        return repository.getUserByLogin(login);
    }

    public User registerUser(User user){
        if(repository.existsByLoginIgnoreCase(user.getLogin()))
            throw new IllegalArgumentException(String.format("User with login %s is already register", user.getLogin()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public String signUpUser(User user) throws UsernameNotFoundException {
        var userFromDb = repository.getUserByLogin(
            user.getLogin()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(!passwordEncoder.matches(user.getPassword(), userFromDb.getPassword()))
            throw new UsernameNotFoundException("Invalid credentials");

        return jwtService.generateToken(userFromDb);
    }
}
