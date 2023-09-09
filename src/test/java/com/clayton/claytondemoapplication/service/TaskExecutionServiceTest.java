package com.clayton.claytondemoapplication.service;

import com.clayton.claytondemoapplication.constants.ResponseMessages;
import com.clayton.claytondemoapplication.dto.ApiResponse;
import com.clayton.claytondemoapplication.dto.TaskExecutionReportDto;
import com.clayton.claytondemoapplication.entity.TaskExecutionReport;
import com.clayton.claytondemoapplication.exception.ResourceNotFoundException;
import com.clayton.claytondemoapplication.repository.TaskExecutionReportRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;

@SpringBootTest
@Slf4j
public class TaskExecutionServiceTest {

    @Autowired
    private TaskExecutionService taskExecutionService;

    @Autowired
    private TaskExecutionReportRepo taskExecutionReportRepo;

    @BeforeEach
    void init(){
        taskExecutionReportRepo.save(new TaskExecutionReport(1l, "WWW2345", "Creating something", "ONGOING", ZonedDateTime.now(), ZonedDateTime.now().plusSeconds(6000), 43l, "Nothing really" ));
        taskExecutionReportRepo.save(new TaskExecutionReport(2l, "EEE2345", "Creating something", "SUCCESS", ZonedDateTime.now(), ZonedDateTime.now().plusSeconds(25), 83l, "Nothing really" ));
    }

    @Test
    void testGetTaskShouldReturnValid(){
        ApiResponse response = taskExecutionService.getTask("WWW2345");
        Assertions.assertEquals(ResponseMessages.SUCCESSFUL.getMessage(), response.getMessage());
    }

    @Test
     void testGetTaskShouldReturnInValid(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            taskExecutionService.getTask("WW2345");
        });
    }

    @Test
    void testUpdateTaskExecutionShouldReturnValid(){
        ApiResponse response = taskExecutionService.updateTaskExecution("WWW2345", new TaskExecutionReportDto("", "Going smooth"));
        log.info("Data -->{}", response.getData());
        Assertions.assertEquals(ResponseMessages.SUCCESSFUL.getMessage(), response.getMessage());
    }

    @Test
    void testUpdateTaskExecutionShouldReturnInValid(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            taskExecutionService.updateTaskExecution("WEW2345", new TaskExecutionReportDto(null, "Going smooth"));
        });
    }

    @Test
    void testCreateTaskShouldReturnValid(){
        ApiResponse response = taskExecutionService.createTask(new TaskExecutionReportDto("Upgrading system architecture", "No Errors encountered yet"));
        log.info("Data -->{}", response.getData());
        Assertions.assertEquals(ResponseMessages.SUCCESSFUL.getMessage(), response.getMessage());
    }


    @Test
    void testTerminateTaskExecutionShouldReturnValid(){
        ApiResponse response = taskExecutionService.terminateTaskExecution("WWW2345");
        log.info("Data -->{}", response.getData());
        Assertions.assertEquals(ResponseMessages.SUCCESSFUL.getMessage(), response.getMessage());
    }


    @Test
    void testTerminateTaskExecutionShouldReturnInValid(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            taskExecutionService.terminateTaskExecution("WEW2345");
        });
    }

    @Test
    void testGetTaskByStatusValid(){
        ApiResponse response = taskExecutionService.getTasksByStatus("ONGOING");
        log.info("{}", response);
        Assertions.assertEquals(ResponseMessages.SUCCESSFUL.getMessage(), response.getMessage());
    }

    @Test
    void testGetTaskExecutionSortedByExecutionTime(){
        ApiResponse response = taskExecutionService.getTaskExecutionSortedByExecutionTime();
        Assertions.assertEquals(ResponseMessages.SUCCESSFUL.getMessage(), response.getMessage());
    }

    @Test
    void testDeleteTaskShouldReturnValid(){
        ApiResponse response = taskExecutionService.deleteTask("WWW2345");
        Assertions.assertEquals(ResponseMessages.SUCCESSFUL.getMessage(), response.getMessage());
    }


    @Test
    void testDeleteTaskShouldReturnInValid(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            taskExecutionService.deleteTask("EWEW2345");
        });
    }

    @Test
    void testGetAllShouldReturnValid(){
        ApiResponse response = taskExecutionService.getAllTaskExecutions();
        log.info("-->{}", response);
        Assertions.assertEquals(ResponseMessages.SUCCESSFUL.getMessage(), response.getMessage());
    }
}
