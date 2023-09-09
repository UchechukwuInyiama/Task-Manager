package com.clayton.claytondemoapplication.repository;

import com.clayton.claytondemoapplication.entity.TaskExecutionReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskExecutionReportRepo extends JpaRepository<TaskExecutionReport, Long> {
    Optional<TaskExecutionReport>findByTaskId(String taskId);

    List<TaskExecutionReport>findAllByTaskStatus(String taskStatus);

    List<TaskExecutionReport> findAllByOrderByExecutionTimeSecondsDesc();
}
