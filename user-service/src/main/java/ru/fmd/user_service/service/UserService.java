package ru.fmd.user_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fmd.user_service.model.Role;
import ru.fmd.user_service.model.User;
import ru.fmd.user_service.model.UserUpdateDto;
import ru.fmd.user_service.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository repository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {
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
        if(user.getRole() == null)
            user.setRole(Role.USER);
        return repository.save(user);
    }

    public String signUpUser(User user) throws UsernameNotFoundException {
        var userFromDb = repository.getUserByLogin(
            user.getLogin()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(!passwordEncoder.matches(user.getPassword(), userFromDb.getPassword()))
            throw new UsernameNotFoundException("Invalid credentials");
        return jwtService.generateToken(userFromDb);
    }

    @Transactional
    public User update(String login, UserUpdateDto updateUser) throws UsernameNotFoundException {
        return repository.getUserByLogin(login)
                .map(updateUser::mapToUser)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public void delete(String login) throws UsernameNotFoundException {
        var user = repository.getUserByLogin(login);
        user.ifPresent(repository::delete);
        user.ifPresentOrElse(repository::delete, () -> {throw new UsernameNotFoundException("User not found");});
    }
}
