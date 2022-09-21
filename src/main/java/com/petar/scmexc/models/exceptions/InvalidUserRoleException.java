package com.marco.scmexc.models.exceptions;

public class InvalidUserRoleException extends RuntimeException {

    public InvalidUserRoleException() {
        super("Invalid user role");
    }

}
