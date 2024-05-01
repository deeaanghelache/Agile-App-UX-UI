package com.unibuc.appbackend.exceptions;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException() {
        super("Role not found!");
    }
}
