package com.clayton.claytondemoapplication.repository;

import com.clayton.claytondemoapplication.entity.TaskStepExecutionReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskStepExecutionReportRepo extends JpaRepository<TaskStepExecutionReport, Long> {
    Optional<TaskStepExecutionReport>findByTaskExecutionId(String taskExecutionId);

    List<TaskStepExecutionReport> findAllByTaskId(String taskId);

    List<TaskStepExecutionReport> findByTaskIdOrderByExecutionTimeSecondsDesc(String taskId);

}
