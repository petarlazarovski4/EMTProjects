package com.marco.scmexc.models.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super(String.format("User with id: %d is not found!",id));
    }

}
