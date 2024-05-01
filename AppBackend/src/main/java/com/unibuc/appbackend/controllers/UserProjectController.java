package com.unibuc.appbackend.controllers;

import com.unibuc.appbackend.entities.Project;
import com.unibuc.appbackend.entities.UserProject;
import com.unibuc.appbackend.services.UserProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/userProject")
@Tag(name = "User - Project")
public class UserProjectController {

    private UserProjectService userProjectService;

    public UserProjectController(UserProjectService userProjectService) {
        this.userProjectService = userProjectService;
    }

    @PostMapping("/createUserProject/{userId}/{projectId}")
    @Operation(summary = "Add an user to a project", description = "Add an user to a project, providing the user id and the project id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User added to the project!"),
            @ApiResponse(responseCode = "404", description = "UserId or ProjectId not found!")
    })
    public ResponseEntity<UserProject> create(@PathVariable("userId") @Parameter(description = "Id of the user added to this project") UUID userId,
                                              @PathVariable("projectId") @Parameter(description = "Id of the project") UUID projectId) {
        return ResponseEntity.ok(userProjectService.create(userId, projectId));
    }

    @GetMapping("/getAllProjects/{userId}")
    @Operation(summary = "Get all projects for given user", description = "Get all projects for given user, by providing the userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found in the database"),
            @ApiResponse(responseCode = "404", description = "User was NOT found in the database")
    })
    public ResponseEntity<List<Project>> getProjectsForGivenUser(@PathVariable("userId") @Parameter(description = "Id of the user") UUID id) {
        return ResponseEntity.ok(userProjectService.getProjectsForGivenUser(id));
    }
}
