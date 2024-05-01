package com.unibuc.appbackend.controllers;

import com.unibuc.appbackend.entities.UserRole;
import com.unibuc.appbackend.services.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("/userRole")
@Tag(name = "User - Role")
public class UserRoleController {

    private UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Operation(summary = "Check if a user is admin", description = "Check if a user is admin by providing their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found in the database"),
            @ApiResponse(responseCode = "404", description = "User was NOT found in the database")
    })
    @GetMapping("/checkAdmin/{id}")
    public ResponseEntity<Boolean> checkIfUserIsAdmin(@PathVariable("id") @Parameter(description = "The id of the user you want to check") UUID userId) {
        return ResponseEntity.ok(userRoleService.checkAdminRoleForGivenUser(userId));
    }

    @Operation(summary = "Check if a user is scrum master", description = "Check if a user is scrum master by providing their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found in the database"),
            @ApiResponse(responseCode = "404", description = "User was NOT found in the database")
    })
    @GetMapping("/checkScrumMaster/{id}")
    public ResponseEntity<Boolean> checkIfUserIsScrumMaster(@PathVariable("id") @Parameter(description = "The id of the user you want to check") UUID userId) {
        return ResponseEntity.ok(userRoleService.checkScrumMasterRoleForGivenUser(userId));
    }

    @Operation(summary = "Add role for user", description = "Add role for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found in the database"),
            @ApiResponse(responseCode = "404", description = "User was NOT found in the database")
    })
    @PostMapping("/addRole/{id}/{role}")
    public ResponseEntity<UserRole> addUserRole(@PathVariable("id") @Parameter(description = "Id of the user") UUID userId, @PathVariable("role") @Parameter(description = "Role name") String role) {
        return ResponseEntity.ok(userRoleService.addUserRole(userId, role));
    }
}
