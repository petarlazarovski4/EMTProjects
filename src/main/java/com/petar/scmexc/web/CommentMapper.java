package com.marco.scmexc.web;

import com.marco.scmexc.models.requests.CommentRequest;
import com.marco.scmexc.models.response.CommentResponse;

import java.util.List;

public interface CommentMapper {

    List<CommentResponse> findAllByMaterialId(Long materialID);
    List<CommentResponse> findAll();
    CommentResponse save(CommentRequest commentRequest);

}
