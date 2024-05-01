package com.unibuc.appbackend.exceptions;

public class UserRoleNotFoundException extends RuntimeException {

    public UserRoleNotFoundException() {
        super("User Role not found!");
    }
}
