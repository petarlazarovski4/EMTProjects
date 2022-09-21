package com.marco.scmexc.models.requests;

import com.marco.scmexc.models.domain.Type;

public class ItemRequest {
    public Long itemID;
    public String fileName;
    public String description;

    public ItemRequest(Long itemID,String fileName,String description){
        this.itemID = itemID;
        this.fileName = fileName;
        this.description = description;
    }
}
