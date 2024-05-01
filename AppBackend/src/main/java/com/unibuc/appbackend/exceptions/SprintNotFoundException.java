package com.unibuc.appbackend.exceptions;

public class SprintNotFoundException extends RuntimeException{

    public SprintNotFoundException() {
        super("Sprint not found!");
    }
}
