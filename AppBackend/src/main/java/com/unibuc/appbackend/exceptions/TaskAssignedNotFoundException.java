package com.unibuc.appbackend.exceptions;

public class TaskAssignedNotFoundException extends RuntimeException {

    public TaskAssignedNotFoundException() {
        super("Task not found!");
    }
}
