package com.unibuc.appbackend.controllers;

import com.unibuc.appbackend.entities.Role;
import com.unibuc.appbackend.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.UUID;

@RestController()
@RequestMapping("/role")
@Tag(name = "Role")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/createRole/{name}")
    @Operation(summary = "Create a new role", description = "Create a new role based on the information received in the request's body")
    @ApiResponse(responseCode = "200", description = "Role created successfully")
    public ResponseEntity<Role> create(@PathVariable("name") @Parameter(description = "Name of the new role: should be user, admin or scrummaster") String name) {
        return ResponseEntity.ok(roleService.create(name));
    }

    @GetMapping("/getRole/{id}")
    @Operation(summary = "Get information about a role", description = "Get information about a certain role by providing their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role was found in the database"),
            @ApiResponse(responseCode = "404", description = "Role was NOT found in the database")
    })
    public ResponseEntity<Role> getRoleById(@PathVariable("id") @Parameter(description = "The uuid of the user you want to get information about") UUID id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @Operation(summary = "Delete a given role", description = "Delete a certain role by providing its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role was found in the database"),
            @ApiResponse(responseCode = "404", description = "Role was NOT found in the database")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Role>  delete(@PathVariable("id") @Parameter(description = "The uuid of the user you want to get information about") UUID id) throws RoleNotFoundException {
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
