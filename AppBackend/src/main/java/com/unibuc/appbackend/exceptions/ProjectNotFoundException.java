package com.unibuc.appbackend.exceptions;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException() {
        super("Project not found!");
    }
}
