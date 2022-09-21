package com.marco.scmexc.models.exceptions;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException(Long id) {
        super(String.format("Question with id: %d is not found!",id));
    }

}
