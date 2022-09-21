package com.marco.scmexc.models.response;

import java.time.ZonedDateTime;

public class AnswerResponse {

    public Long id;
    public String question;
    public String answer;
    public int upVotes;
    public int downVotes;
    public String answeredBy;
    public ZonedDateTime time;

    public AnswerResponse(Long id, String question,String answer,int upVotes,int downVotes,String answeredBy,ZonedDateTime time) {
        this.id = id;
        this.question= question;
        this.answer=answer;
        this.upVotes=upVotes;
        this.downVotes=downVotes;
        this.answeredBy=answeredBy;
        this.time=time;

    }

    public static AnswerResponse of(Long id, String question,String answer,int upVotes,int downVotes,String createdBy,ZonedDateTime time) {
        return  new AnswerResponse(id, question, answer, upVotes, downVotes,createdBy,time);
    }

}
