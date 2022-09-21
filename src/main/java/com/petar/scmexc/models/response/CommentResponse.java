package com.marco.scmexc.models.response;

import java.time.ZonedDateTime;

public class CommentResponse {

    public Long id;
    public String description;
    public Integer upVotes;
    public Integer downVotes;
    public String createdBy;
    public ZonedDateTime datePosted;

    public CommentResponse(Long id, String description, Integer upVotes,
                           Integer downVotes, String createdBy,
                           ZonedDateTime datePosted) {
        this.id = id;
        this.description = description;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.createdBy = createdBy;
        this.datePosted = datePosted;
    }

    public static CommentResponse of(Long id, String description, Integer upVotes,Integer downVotes, String createdBy,ZonedDateTime datePosted){
        return new CommentResponse(id,description,upVotes,downVotes,createdBy,datePosted);
    }
}
