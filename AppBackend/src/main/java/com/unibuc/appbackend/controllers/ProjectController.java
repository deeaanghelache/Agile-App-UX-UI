package com.unibuc.appbackend.controllers;

import com.unibuc.appbackend.entities.Project;
import com.unibuc.appbackend.services.ProjectService;
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
@RequestMapping("/project")
@Tag(name = "Project")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/createProject")
    @Operation(summary = "Create a project", description = "Create a new project based on the information received in the request's body")
    @ApiResponse(responseCode = "200", description = "Project created successfully")
    public ResponseEntity<Project> create(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.create(project));
    }

    @GetMapping("/getAllProjects")
    @Operation(summary = "Get all projects", description = "Returns all projects in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list containing all the projects in the database")
    })
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAll());
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update project", description = "Update the description of a given project by providing the project id and the new description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project was found in the database"),
            @ApiResponse(responseCode = "404", description = "Project was NOT found in the database")
    })
    public ResponseEntity<Project> update(@PathVariable("id") @Parameter(description = "Id of the project you want to update") UUID projectId, @RequestBody String description) {
        return ResponseEntity.ok(projectService.update(projectId, description));
    }

    @Operation(summary = "Delete a given project", description = "Delete a certain project by providing its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project was found in the database"),
            @ApiResponse(responseCode = "404", description = "Project was NOT found in the database")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Project> delete(@PathVariable @Parameter (description = "The uuid of the user you want to get information about") UUID id) {
        projectService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
