package com.unibuc.appbackend.exceptions.advice;

import com.unibuc.appbackend.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<?> handle(UserAlreadyExistsException userAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(userAlreadyExistsException.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<?> handle(UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userNotFoundException.getMessage());
    }

    @ExceptionHandler({ProjectNotFoundException.class})
    public ResponseEntity<?> handle(ProjectNotFoundException projectNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(projectNotFoundException.getMessage());
    }

    @ExceptionHandler({SprintNotFoundException.class})
    public ResponseEntity<?> handle(SprintNotFoundException sprintNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(sprintNotFoundException.getMessage());
    }

    @ExceptionHandler({RoleNotFoundException.class})
    public ResponseEntity<?> handle(RoleNotFoundException roleNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(roleNotFoundException.getMessage());
    }

    @ExceptionHandler({TaskAssignedNotFoundException.class})
    public ResponseEntity<?> handle(TaskAssignedNotFoundException taskAssignedNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(taskAssignedNotFoundException.getMessage());
    }

    @ExceptionHandler({SubtaskNotFoundException.class})
    public ResponseEntity<?> handle(SubtaskNotFoundException subtaskNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(subtaskNotFoundException.getMessage());
    }
}
