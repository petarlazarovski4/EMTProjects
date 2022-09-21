package com.marco.scmexc.models.requests;


public class CourseRequest {

    public final Long id;
    public final String name;
    public final String code;
    public final String description;

    public CourseRequest(Long id, String name, String code, String description) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
    }
}
