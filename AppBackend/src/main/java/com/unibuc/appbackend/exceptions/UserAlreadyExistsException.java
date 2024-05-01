package com.unibuc.appbackend.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException() {
        super("There is already an user with this email!");
    }
}
