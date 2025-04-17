package ru.fmd.todo_service.todo_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fmd.todo_service.todo_service.model.Task;

import java.util.List;

public interface TasksRepository extends JpaRepository<Task, Long> {
    List<Task> findByLogin(String login);
}