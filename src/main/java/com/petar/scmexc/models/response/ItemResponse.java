package com.marco.scmexc.models.response;

import com.marco.scmexc.models.domain.Type;

import java.time.ZonedDateTime;

public class ItemResponse {
    public String name;
    public String question;
    public String url;
    public Type type;
    public ZonedDateTime timePosted;
    public long itemID;
    public Long questionID;


    public ItemResponse(String name,String url,Type type,ZonedDateTime timePosted,String question,long itemID, Long questionID){
        this.name=name;
        this.url=url;
        this.type=type;
        this.timePosted=timePosted;
        this.question=question;
        this.itemID= itemID;
        this.questionID = questionID;
    }

    public static ItemResponse of(String name,String url,Type type,ZonedDateTime timePosted,String question,long itemID, Long questionID){
        return new ItemResponse(name, url, type,timePosted, question,itemID, questionID);
    }
}
