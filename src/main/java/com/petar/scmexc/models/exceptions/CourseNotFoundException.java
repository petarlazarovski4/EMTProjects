package com.marco.scmexc.models.exceptions;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(Long id) {
        super(String.format("Course with id: %d is not found!",id));
    }

}
