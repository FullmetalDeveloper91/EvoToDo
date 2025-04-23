package ru.fmd.log_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fmd.log_service.model.TaskLog;

public interface TaskLogRepository extends JpaRepository<TaskLog, Long> {
}
