package ru.fmd.user_service.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fmd.user_service.user_service.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByLogin(String login);

    Optional<User> getUserByLoginAndPassword(String login, String password);

    boolean existsByLoginIgnoreCase(String login);
}
