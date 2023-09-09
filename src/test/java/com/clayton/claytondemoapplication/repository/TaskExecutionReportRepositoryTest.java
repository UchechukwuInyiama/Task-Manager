package com.clayton.claytondemoapplication.repository;

import com.clayton.claytondemoapplication.entity.TaskExecutionReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TaskExecutionReportRepositoryTest {
    @Autowired
    private TaskExecutionReportRepo taskExecutionReportRepo;

    @BeforeEach
    void init(){
        taskExecutionReportRepo.save(new TaskExecutionReport(1l, "WWW2345", "Creating something", "ONGOING", ZonedDateTime.now(), ZonedDateTime.now().plusSeconds(25), 23l, "Nothing really" ));
        taskExecutionReportRepo.save(new TaskExecutionReport(2l, "EEE2345", "Creating something", "SUCCESS", ZonedDateTime.now(), ZonedDateTime.now().plusSeconds(25), 23l, "Nothing really" ));
    }

    @Test
    void testFindByTaskIdShouldReturnValid(){
        Optional<TaskExecutionReport> optional = taskExecutionReportRepo.findByTaskId("WWW2345");
        Assertions.assertTrue(!optional.isEmpty());
    }

    @Test
    void testFindByTaskIdShouldReturnIValid(){
        Optional<TaskExecutionReport> optional = taskExecutionReportRepo.findByTaskId("EE2345");
        Assertions.assertFalse(!optional.isEmpty());
    }

    @Test
    void testFindAllByTaskStatusShouldReturnValid(){
        List<TaskExecutionReport> running = taskExecutionReportRepo.findAllByTaskStatus("SUCCESS");
        Assertions.assertTrue(running.size() == 1);
    }

    @Test
    void testFindAllByTaskStatusShouldReturnInValid(){
        List<TaskExecutionReport> running = taskExecutionReportRepo.findAllByTaskStatus("FAILURE");
        Assertions.assertFalse(running.size() < 0);
    }

    @Test
    void testFindAllOrderByExecutionTimeSecondsDesc(){
        List<TaskExecutionReport> all = taskExecutionReportRepo.findAllByOrderByExecutionTimeSecondsDesc();
        Assertions.assertTrue(all.size() > 0);
    }
}
