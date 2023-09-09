package com.clayton.claytondemoapplication.repository;

import com.clayton.claytondemoapplication.entity.TaskStepExecutionReport;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class TaskStepExecutionRepoTest {
    @Autowired
    private TaskStepExecutionReportRepo taskStepExecutionReportRepo;

    @BeforeEach
    void init(){
      taskStepExecutionReportRepo.save(new TaskStepExecutionReport(1L, "EESR345", "RUNNING", "WWEST567", "Creating entities", ZonedDateTime.now(), ZonedDateTime.now().plusSeconds(25), ZonedDateTime.now().plusSeconds(50), 30l, "Nothing there"));
      taskStepExecutionReportRepo.save(new TaskStepExecutionReport(2L, "EFSR345", "RUNNING", "WWEST567", "Creating services", ZonedDateTime.now(), ZonedDateTime.now().plusSeconds(25), ZonedDateTime.now().plusSeconds(50), 35l, "Difficulties implementing services"));

    }

    @Test
    void testFindByTestIdOrderByExecutionTimeSecondsDescShouldReturnValid(){
        List<TaskStepExecutionReport> order = taskStepExecutionReportRepo
                .findByTaskIdOrderByExecutionTimeSecondsDesc("WWEST567");
        log.info("Results -->{}", order);
        Assertions.assertTrue(order.size() > 0);
    }

    @Test
    void testFindByTestIdOrderByExecutionTimeSecondsDescShouldReturnInValid(){
        List<TaskStepExecutionReport> order = taskStepExecutionReportRepo
                .findByTaskIdOrderByExecutionTimeSecondsDesc("EST567");
        log.info("Results -->{}", order);
        Assertions.assertFalse(order.size() < 0);
    }

    @Test
    void testFindByTaskExecutionIdShouldReturnValid(){
        Optional<TaskStepExecutionReport> executionReport =
                taskStepExecutionReportRepo.findByTaskExecutionId("EESR345");
        Assertions.assertTrue(!executionReport.isEmpty());
    }

    @Test
    void testFindByTaskExecutionIdShouldReturnInValid(){
        Optional<TaskStepExecutionReport> executionReport =
                taskStepExecutionReportRepo.findByTaskExecutionId("WWRST567");
        Assertions.assertFalse(!executionReport.isEmpty());
    }

    @Test
    void testFindAllByTaskIdShouldReturnValid(){
        List<TaskStepExecutionReport> all =
                taskStepExecutionReportRepo.findAllByTaskId("WWEST567");
        Assertions.assertTrue(all.size() > 0);
    }

    @Test
    void testFindAllByTaskIdShouldReturnInValid(){
        List<TaskStepExecutionReport> all =
                taskStepExecutionReportRepo.findAllByTaskId("WWE5T567");
        Assertions.assertFalse(all.size() < 0);
    }
}
