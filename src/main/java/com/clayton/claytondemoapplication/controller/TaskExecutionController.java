package com.clayton.claytondemoapplication.controller;

import com.clayton.claytondemoapplication.dto.ApiResponse;
import com.clayton.claytondemoapplication.dto.TaskExecutionReportDto;
import com.clayton.claytondemoapplication.service.TaskExecutionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/taskExecution")
public class TaskExecutionController {
    private final TaskExecutionService taskExecutionService;

    @ApiOperation(value = "Creates new task", notes = "A DTO is provided to create the task" +
            "the DTO is then mapped to the TaskExecutionReport DTO amd saved")
    @PostMapping("/createTaskExecution")
    public ResponseEntity<?>createTask(@RequestBody TaskExecutionReportDto taskExecutionReportDto){
        ApiResponse response = taskExecutionService.createTask(taskExecutionReportDto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "fetches all tasks and their step executions", notes = " the task execution ID is passed which fetches the task and the steps associated with the tasks. an update() " +
            " an update status method is run which updates the status of the task by considering the status of the task steps. For" +
            " example if one task step fails the entire task fails.")
    @GetMapping("task/{taskExecutionId}")
    public ResponseEntity<?> getTaskByTaskExecutionId(@PathVariable("taskExecutionId") String taskExecutionId){
        return ResponseEntity.ok(taskExecutionService.getTask(taskExecutionId));
    }

    @ApiOperation(value = "updates the taskExecution", notes = " the taskExecutionID is passed which fetched the content of the task. A Dto containing the fields to be updated" +
            " is passed and the contents of the task are updated via the Dto values provided. All fields are required ")
    @PutMapping("updateTask/{taskExecutionId}")
    public ResponseEntity<?> updateTaskExecution(@PathVariable("taskExecutionId") String taskExecutionId, @RequestBody TaskExecutionReportDto taskExecutionReportDto){
        return ResponseEntity.ok(taskExecutionService.updateTaskExecution(taskExecutionId, taskExecutionReportDto));
    }

    @ApiOperation(value = "fetches all the tasks sorted by their execution time in descending order. That is the tasks that took the longest to accomplish are displayed first",
            notes = "no parameters are passed all tasks are fetched from the database ")
    @GetMapping("/tasks/sortedByExecutionTime")
    public ResponseEntity<?>getAllTasksSortedByExecutionTime(){
        return ResponseEntity.ok(taskExecutionService.getTaskExecutionSortedByExecutionTime());
    }

    @ApiOperation(value = "terminates running tasks", notes = "the taskExecutionID is passed, the task associated with the ID is fetched and terminated. This works by passing an endtime value to the task ")
    @GetMapping("/terminateTask/{taskExecutionId}")
    public ResponseEntity<?>terminateTaskByTaskId(@PathVariable("taskExecutionId") String taskExecutionId){
        return ResponseEntity.ok(taskExecutionService.terminateTaskExecution(taskExecutionId));
    }

    @ApiOperation(value = "deletes task and associated steps", notes = " the taskExecutionID is passed which fetches the associated task amd deletes it. In addition, all" +
            " steps associated with the task are also deleted ")
    @DeleteMapping("/deleteTask/{taskExecutionId}")
    public ResponseEntity<?>deleteTaskByTaskId(@PathVariable("taskExecutionId") String taskExecutionId){
        return ResponseEntity.ok(taskExecutionService.deleteTask(taskExecutionId));
    }

    @ApiOperation(value = "fetches all tasks via their status", notes = " the taskStatus is passed which fetches corresponding tasks with the status an displays the data." +
            " For example all RUNNING tasks can be viewed ")
    @GetMapping("/tasks/status/{taskStatus}")
    public ResponseEntity<?>getTasksByStatus(@PathVariable("taskStatus") String taskStatus){
        return ResponseEntity.ok(taskExecutionService.getTasksByStatus(taskStatus));
    }

    @ApiOperation(value = "fetches all tasks", notes = " this endpoint fetches all task created from the database ")
    @GetMapping("/tasks/allTasks")
    public ResponseEntity<?>getAllTaskExecutions(){
        return ResponseEntity.ok(taskExecutionService.getAllTaskExecutions());
    }
}
