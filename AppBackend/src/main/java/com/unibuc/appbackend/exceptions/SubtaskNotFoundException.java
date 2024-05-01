package com.unibuc.appbackend.exceptions;

public class SubtaskNotFoundException extends RuntimeException {

    public SubtaskNotFoundException() {
        super("Subtask not found!");
    }
}
