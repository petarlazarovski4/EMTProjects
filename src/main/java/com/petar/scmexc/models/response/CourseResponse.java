package com.marco.scmexc.models.response;

import com.marco.scmexc.models.domain.SmxUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class CourseResponse {

    public final Long id;
    public final String name;
    public final String code;
    public final String description;
    public final LocalDate dateCreated;
    public final Set<SmxUser> moderators;

    private CourseResponse(Long id, String name, String code, String description, LocalDate dateCreated, Set<SmxUser> moderators) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.dateCreated = dateCreated;
        this.moderators = moderators;
    }

    public static CourseResponse of(Long id, String name, String code, String description, LocalDate dateCreated, Set<SmxUser> moderators){
        return new CourseResponse(id, name, code, description, dateCreated, moderators);
    }
}
