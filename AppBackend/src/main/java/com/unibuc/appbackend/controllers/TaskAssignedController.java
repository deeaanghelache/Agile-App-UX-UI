package com.unibuc.appbackend.controllers;

import com.unibuc.appbackend.entities.TaskAssigned;
import com.unibuc.appbackend.services.TaskAssignedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/task")
@Tag(name = "Task")
public class TaskAssignedController {

    private TaskAssignedService taskAssignedService;

    public TaskAssignedController(TaskAssignedService taskAssignedService) {
        this.taskAssignedService = taskAssignedService;
    }

    @GetMapping("/getAllTasksForGivenUser/{id}")
    @Operation(summary = "Get all tasks for given user", description = "Get all tasks for given user, by providing the user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found in the database"),
            @ApiResponse(responseCode = "404", description = "User was NOT found in the database")
    })
    public ResponseEntity<List<TaskAssigned>> getAllTasksForGivenUser(@PathVariable("id") @Parameter(description = "The id of the user") UUID userId) {
        return ResponseEntity.ok(taskAssignedService.getAllTasksForGivenUser(userId));
    }

    @GetMapping("/getAllTasksForGivenProject/{id}")
    @Operation(summary = "Get all tasks for given project", description = "Get all tasks for given project, by providing the project id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found in the database"),
            @ApiResponse(responseCode = "404", description = "User was NOT found in the database")
    })
    public ResponseEntity<List<TaskAssigned>> getAllTasksForGivenProject(@PathVariable("id") @Parameter(description = "The id of the project") UUID projectId) {
        return ResponseEntity.ok(taskAssignedService.getAllTasksForGivenProject(projectId));
    }

    @PostMapping("/createTask/{userId}/{projectId}/{sprintId}")
    @Operation(summary = "Assign a task to a user", description = "Assign a task to a user, providing the user id, project id, sprint id and description of the task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task created and assigned successfully"),
            @ApiResponse(responseCode = "404", description = "UserId or ProjectId or SprintId not found!")
    })
    public ResponseEntity<TaskAssigned> create(@PathVariable("userId") @Parameter(description = "Id of the user assigned to this task") UUID userId,
                                               @PathVariable("projectId") @Parameter(description = "Id of the project") UUID projectId,
                                               @PathVariable("sprintId") @Parameter(description = "Id of the sprint this task belongs to") UUID sprintId,
                                               @RequestBody String description) {
        return ResponseEntity.ok(taskAssignedService.create(userId, projectId, sprintId, description));
    }

    @PutMapping("/updateStatus/{taskId}/{status}")
    @Operation(summary = "Update status of task", description = "Update the status of a given task by providing the task id and the new status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found!")
    })
    public ResponseEntity<TaskAssigned> updateStatus(@PathVariable("taskId") @Parameter(description = "The id of the task you want to edit") UUID taskId, @PathVariable("status") @Parameter(description = "The new status, should be one of todo, done, inprogress, nicetohave") String status) {
        return ResponseEntity.ok(taskAssignedService.updateStatus(taskId, status));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a task", description = "Delete a task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task was found in the db and successfully deleted!"),
            @ApiResponse(responseCode = "404", description = "Task not found in the db!")
    })
    public ResponseEntity<?> delete(@PathVariable("id") @Parameter(description = "Id of the task you want to delete") UUID taskId) {
        taskAssignedService.delete(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
