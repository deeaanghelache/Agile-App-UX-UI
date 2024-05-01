package com.unibuc.appbackend.controllers;

import com.unibuc.appbackend.entities.Sprint;
import com.unibuc.appbackend.services.SprintService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

@RestController()
@RequestMapping("/sprint")
@Tag(name = "Sprint")
public class SprintController {

    private SprintService sprintService;

    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @PostMapping("/createSprint")
    @Operation(summary = "Create a sprint", description = "Create a new sprint based on the information received in the request's body")
    @ApiResponse(responseCode = "200", description = "Sprint created successfully")
    public ResponseEntity<Sprint> create(@RequestBody Sprint sprint) {
        return ResponseEntity.ok(sprintService.create(sprint));
    }

    @GetMapping("/getById/{id}")
    @Operation(summary = "Get sprint by id", description = "Get information about a sprint by providing its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sprint was found!"),
            @ApiResponse(responseCode = "404", description = "Sprint was not found!")
    })
    public ResponseEntity<Sprint> getById(@PathVariable("id") @Parameter(description = "Id of the sprint") UUID sprintId) {
        return ResponseEntity.ok(sprintService.getById(sprintId));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update sprint", description = "Update the deadline of a given sprint by providing the sprint id and the new deadline")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sprint was found in the database"),
            @ApiResponse(responseCode = "404", description = "Sprint was NOT found in the database")
    })
    public ResponseEntity<Sprint> update(@PathVariable("id") @Parameter(description = "Id of the sprint you want to modify") UUID sprintId, @RequestBody LocalDate deadline) {
        return ResponseEntity.ok(sprintService.update(sprintId, deadline));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a given sprint", description = "Delete a certain sprint by providing their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sprint was found in the database"),
            @ApiResponse(responseCode = "404", description = "Sprint was NOT found in the database")
    })
    public ResponseEntity<?> delete(@PathVariable("id") @Parameter(description = "Id of the sprint") UUID sprintId) {
        sprintService.delete(sprintId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
