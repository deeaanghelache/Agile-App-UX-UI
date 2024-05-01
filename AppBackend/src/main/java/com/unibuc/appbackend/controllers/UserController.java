package com.unibuc.appbackend.controllers;

import com.unibuc.appbackend.entities.User;
import com.unibuc.appbackend.services.UserService;
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

@RestController
@RequestMapping("/user")
@Tag(name = "User")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Create an user", description = "Creates a new user based on the information received in the request's body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The user has been successfully created!"),
            @ApiResponse(responseCode = "409", description = "There is already an user with the specified email!")
    })
    public ResponseEntity<User> create(@RequestBody @Parameter(description = "User data provided by the register form") User user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Login an user", description = "Checks if the password and email provided in the request's body are correct")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user has been successfully logged in!"),
            @ApiResponse(responseCode = "401", description = "Email or password incorrect!")
    })
    public ResponseEntity<User> login(@RequestBody @Parameter(description = "User data provided by the login form") User user) {
        return ResponseEntity.ok(userService.login(user));
    }

    @Operation(summary = "Get information about an user", description = "Get information about a certain user by providing their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found in the database"),
            @ApiResponse(responseCode = "404", description = "User was NOT found in the database")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") @Parameter(description = "The uuid of the user you want to get information about") UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Get all users", description = "Returns all users in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list containing all the users in the database")
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Change password for given user", description = "Change the password of a certain user by providing their id and new password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found in the database"),
            @ApiResponse(responseCode = "404", description = "User was NOT found in the database")
    })
    @PostMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable("id") @Parameter(description = "The uuid of the user") UUID id, @RequestBody String password){
        userService.changePassword(id, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete a given user", description = "Delete a certain user by providing their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found in the database"),
            @ApiResponse(responseCode = "404", description = "User was NOT found in the database")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") @Parameter(description = "The uuid of the user") UUID id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
