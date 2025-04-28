package ru.fmd.log_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.fmd.log_service.model.TaskLog;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskLogRepository extends JpaRepository<TaskLog, Long> {
    @Query("select t from TaskLog t where upper(t.login) = upper(?1) and t.createdAt between ?2 and ?3")
    List<TaskLog> findLogsByLogin(String login, LocalDateTime createdAtStart, LocalDateTime createdAtEnd);

    @Query("select t from TaskLog t where t.createdAt between ?1 and ?2")
    List<TaskLog> findLogs(LocalDateTime createdAtStart, LocalDateTime createdAtEnd);
}