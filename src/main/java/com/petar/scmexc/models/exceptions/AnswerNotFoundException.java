package com.marco.scmexc.models.exceptions;

public class AnswerNotFoundException extends RuntimeException {

    public AnswerNotFoundException(Long id) {
        super(String.format("Answer with id: %d is not found!",id));
    }

}
