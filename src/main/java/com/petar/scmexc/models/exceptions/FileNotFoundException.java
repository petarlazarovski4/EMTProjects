package com.marco.scmexc.models.exceptions;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(Long id) {
        super(String.format("File with id: %d is not found!",id));
    }

}
