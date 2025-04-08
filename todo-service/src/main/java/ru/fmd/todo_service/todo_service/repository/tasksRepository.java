package ru.fmd.todo_service.todo_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fmd.todo_service.todo_service.model.Task;

public interface tasksRepository extends JpaRepository<Task, Long> {
}
