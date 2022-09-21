package com.marco.scmexc.models.response;

import com.marco.scmexc.models.domain.Type;

import java.time.ZonedDateTime;

public class SmxFileResponse {
    public String name;
    public String url;
    public Type type;
    public long size;
    public ZonedDateTime timePosted;

    public SmxFileResponse(String name,String url,Type type, long size,ZonedDateTime timePosted){
        this.name=name;
        this.url=url;
        this.type=type;
        this.size=size;
        this.timePosted=timePosted;
    }
    public static SmxFileResponse of(String name,String url,Type type,long size,ZonedDateTime timePosted){
        return  new SmxFileResponse(name,url,type,size,timePosted);
    }


}
