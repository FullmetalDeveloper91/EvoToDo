package ru.fmd.todo_service.todo_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.fmd.todo_service.todo_service.model.Status;
import ru.fmd.todo_service.todo_service.model.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TasksRepository extends JpaRepository<Task, Long> {
    List<Task> findByLogin(String login);

    @Query("""
            select t from Task t
            where upper(t.login) = upper(:login)
                        and t.status = :status
                        and t.createdAt between :createdAtStart and :createdAtEnd
            order by t.createdAt""")
    List<Task> findActualTasks(
            @Param("login") @Nullable String login,
            @Param("status") @NonNull Status status,
            @Param("createdAtStart") @NonNull LocalDateTime createdAtStart,
            @Param("createdAtEnd") @NonNull LocalDateTime createdAtEnd);

    @Query("""
            select t from Task t
            where t.status = :status and t.createdAt between :createdAtStart and :createdAtEnd
            order by t.createdAt""")
    List<Task> findActualTasks(
            @Param("status") @NonNull Status status,
            @Param("createdAtStart") @NonNull LocalDateTime createdAtStart,
            @Param("createdAtEnd") @NonNull LocalDateTime createdAtEnd);
}