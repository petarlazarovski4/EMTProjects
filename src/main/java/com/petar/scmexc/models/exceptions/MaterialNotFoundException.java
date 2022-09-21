package com.marco.scmexc.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MaterialNotFoundException extends RuntimeException {
    public MaterialNotFoundException(Long id){
        super(String.format("Material with id: %d was not found",id));
    }
}
