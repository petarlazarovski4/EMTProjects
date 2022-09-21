package com.marco.scmexc.web.impl;

import com.marco.scmexc.models.domain.Comment;
import com.marco.scmexc.models.domain.Material;
import com.marco.scmexc.models.requests.CommentRequest;
import com.marco.scmexc.models.response.CommentResponse;
import com.marco.scmexc.models.response.MaterialResponse;
import com.marco.scmexc.repository.CommentRepository;
import com.marco.scmexc.services.CommentService;
import com.marco.scmexc.web.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentMapperImpl implements CommentMapper {

    @Autowired
    private CommentService commentService;

    @Override
    public List<CommentResponse> findAll() {
        return commentService.findAll()
                .stream()
                .map(this::mapCommentToCommentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse save(CommentRequest commentRequest) {
        Comment comment = commentService.save(commentRequest);
        return mapCommentToCommentResponse(comment);
    }

    @Override
    public List<CommentResponse> findAllByMaterialId(Long materialID) {
        return commentService.getAllCommentForMaterial(materialID)
                .stream()
                .map(this::mapCommentToCommentResponse)
                .collect(Collectors.toList());
    }

    private CommentResponse mapCommentToCommentResponse(Comment comment){
        return CommentResponse.of(comment.getId(),comment.getDescription(), comment.getUpvotes(), comment.getDownvotes(),
                comment.getCreatedBy() != null ? comment.getCreatedBy().getEmail() : null,comment.getDatePosted());
    }
}
