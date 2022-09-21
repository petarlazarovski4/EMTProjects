package com.marco.scmexc.models.exceptions;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(Long id) {
        super(String.format("Item with id: %d is not found!",id));
    }

}
