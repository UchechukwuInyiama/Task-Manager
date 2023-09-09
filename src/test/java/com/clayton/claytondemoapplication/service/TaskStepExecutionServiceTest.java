package com.clayton.claytondemoapplication.service;

import com.clayton.claytondemoapplication.constants.ResponseMessages;
import com.clayton.claytondemoapplication.constants.TaskStatus;
import com.clayton.claytondemoapplication.dto.ApiResponse;
import com.clayton.claytondemoapplication.dto.TaskStepExecutionReportDto;
import com.clayton.claytondemoapplication.entity.TaskExecutionReport;
import com.clayton.claytondemoapplication.entity.TaskStepExecutionReport;
import com.clayton.claytondemoapplication.exception.ResourceNotFoundException;
import com.clayton.claytondemoapplication.repository.TaskExecutionReportRepo;
import com.clayton.claytondemoapplication.repository.TaskStepExecutionReportRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;

@SpringBootTest
@Slf4j
public class TaskStepExecutionServiceTest {

    @Autowired
    private TaskStepExecutionService taskStepExecutionService;

    @Autowired
    private TaskStepExecutionReportRepo taskStepExecutionReportRepo;

    @Autowired
    private TaskExecutionReportRepo taskExecutionReportRepo;

    @BeforeEach
    void init(){
        taskExecutionReportRepo.save(new TaskExecutionReport(1l, "WWW2345", "Creating something", "ONGOING", ZonedDateTime.now(), ZonedDateTime.now().plusSeconds(6000), 43l, "Nothing really" ));
        taskExecutionReportRepo.save(new TaskExecutionReport(2l, "EEE2345", "Creating something", "SUCCESS", ZonedDateTime.now(), ZonedDateTime.now().plusSeconds(25), 83l, "Nothing really" ));
        taskStepExecutionReportRepo.save(new TaskStepExecutionReport(1L, "WWAR2345", "RUNNING", "WWW2345", "Creating Entities", ZonedDateTime.now().plusSeconds(2000), ZonedDateTime.now().plusSeconds(5000), ZonedDateTime.now().plusSeconds(200000), 23L, "No errors yet"));
        taskStepExecutionReportRepo.save(new TaskStepExecutionReport(2L, "WWV2345", "RUNNING", "WWW2345", "Creating services", ZonedDateTime.now().plusSeconds(4000), ZonedDateTime.now().plusSeconds(5000), ZonedDateTime.now().plusSeconds(200000), 25L, "some errors yet"));
        taskStepExecutionReportRepo.save(new TaskStepExecutionReport(3L, "ESY2345", "RUNNING", "WWW2345", "Creating services", ZonedDateTime.now().plusSeconds(4000), ZonedDateTime.now().plusSeconds(5000), ZonedDateTime.now().plusSeconds(200000), 25L, "some errors yet"));

    }

    @Test
    void testAddTaskReportShouldReturnValid(){
        ApiResponse response = taskStepExecutionService
                .addTaskReport("EEE2345", new TaskStepExecutionReportDto("Creating of services",
                        "No errors yet", String.valueOf(TaskStatus.SUCCESS)));
        Assertions.assertEquals(ResponseMessages.SUCCESSFUL.getMessage(), response.getMessage());
    }

    @Test
    void testAddTaskReportShouldReturnInValid(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            taskStepExecutionService
                    .addTaskReport("WWT2345", new TaskStepExecutionReportDto("Creating of services",
                            "No errors yet", String.valueOf(TaskStatus.SUCCESS)));
        });
    }

    @Test
    void testUpdateTaskStepExecutionReportShouldReturnValid(){
        ApiResponse response = taskStepExecutionService
                .updateTaskStepExecutionReport("ESY2345",
                        new TaskStepExecutionReportDto("updating services", "failed implementation", "FAILURE"));
        Assertions.assertEquals(ResponseMessages.SUCCESSFUL.getMessage(), response.getMessage());
    }

    @Test
    void testUpdateTaskStepExecutionReportShouldReturnInValid(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            taskStepExecutionService
                    .updateTaskStepExecutionReport("WWD2345",
                            new TaskStepExecutionReportDto("updating services", "failed implementation", "FAILURE"));
        });
    }

    @Test
    void testGetTaskExecutionStepReportPerTaskIdShouldReturnValid(){
        ApiResponse response = taskStepExecutionService.getTaskExecutionStepReportPerTaskId("EEE2345");
        Assertions.assertEquals(ResponseMessages.SUCCESSFUL.getMessage(), response.getMessage());
    }

    @Test
    void testGetTaskExecutionStepReportPerTaskIdShouldReturnInValid(){
       Assertions.assertThrows(ResourceNotFoundException.class, () -> {
           taskStepExecutionService.getTaskExecutionStepReportPerTaskId("WEW2345");
       });
    }

    @Test
    void testTerminateTaskStepExecutionShouldReturnValid(){
        ApiResponse response = taskStepExecutionService.terminateTaskStepExecution("WWAR2345");
        log.info("Messages -->{}", response);
        Assertions.assertEquals(ResponseMessages.SUCCESSFUL.getMessage(), response.getMessage());
    }

    @Test
    void testTerminateTaskStepExecutionShouldReturnInValid(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            taskStepExecutionService.terminateTaskStepExecution("WWE34356");
        });
    }

    @Test
    void testDeleteTaskStepExecutionReportShouldReturnValid(){
        ApiResponse response = taskStepExecutionService.deleteTaskStepExecutionReport("WWV2345");
        Assertions.assertEquals(ResponseMessages.SUCCESSFUL.getMessage(), response.getMessage());
    }

    @Test
    void testDeleteTaskStepExecutionReportShouldReturnInValid(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            taskStepExecutionService.deleteTaskStepExecutionReport("WAV2345");
        });
    }


    @Test
    void testGetTaskStepExecutionReportExecutionTimeByTaskIdShouldReturnValid(){
        ApiResponse response = taskStepExecutionService.getTaskStepReportsByTaskIdOrderedByExecutionTime("WWW2345");
        log.info("Test{}", response);
        Assertions.assertEquals(ResponseMessages.SUCCESSFUL.getMessage(), response.getMessage());
    }

    @Test
    void testGetTaskStepExecutionReportExecutionTimeByTaskIdShouldReturnInValid(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            taskStepExecutionService.getTaskStepReportsByTaskIdOrderedByExecutionTime("WAW2345");
        });
    }

}
