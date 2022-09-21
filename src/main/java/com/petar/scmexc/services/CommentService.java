package com.marco.scmexc.services;

import com.marco.scmexc.models.domain.Comment;
import com.marco.scmexc.models.domain.Material;
import com.marco.scmexc.models.domain.SmxUser;
import com.marco.scmexc.models.exceptions.CommentNotFoundException;
import com.marco.scmexc.models.exceptions.MaterialNotFoundException;
import com.marco.scmexc.models.exceptions.UserNotFoundException;
import com.marco.scmexc.models.requests.CommentRequest;
import com.marco.scmexc.repository.CommentRepository;
import com.marco.scmexc.repository.MaterialRepository;
import com.marco.scmexc.repository.SmxUserRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class CommentService {


    private final CommentRepository commentRepository;

    private final SmxUserRepository userRepository;

    private final MaterialRepository materialRepository;

    public CommentService(CommentRepository commentRepository, SmxUserRepository userRepository, MaterialRepository materialRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.materialRepository = materialRepository;
    }

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    public List<Comment> getAllCommentForMaterial(Long materialID){
        return this.commentRepository.findALlByMaterialId(materialID);
    }

    public Comment save(CommentRequest commentRequest){
        Comment comment = new Comment();
        comment.setId(commentRequest.id);
        comment.setDescription(commentRequest.description);
        SmxUser createdBy = userRepository.findSmxUserByEmail(commentRequest.createdByUserEmail).orElse(null);
        comment.setCreatedBy(createdBy);
        Material material = materialRepository.findById(commentRequest.materialId).orElseThrow(()-> new MaterialNotFoundException(commentRequest.materialId));
        comment.setMaterial(material);
        comment.setUpvotes(0);
        comment.setDownvotes(0);
        comment.setDatePosted(ZonedDateTime.now());
        return this.commentRepository.save(comment);
    }

    public boolean incUpVotes(Long commentID,Long userID) {
        SmxUser user = this.userRepository.findById(userID).orElseThrow(()-> new UserNotFoundException(userID));
        Comment comment = this.commentRepository.findById(commentID).orElseThrow(()-> new CommentNotFoundException(commentID));
        if(!comment.getUpVotedBy().contains(user)){
            int upVotes = comment.getUpvotes()+1;
            comment.getUpVotedBy().add(user);
            comment.setUpvotes(upVotes);
            this.commentRepository.save(comment);
            return true;
        }
        return false;
    }

    public boolean incDownVotes(Long commentID,Long userID) {
        SmxUser user = this.userRepository.findById(userID).orElseThrow(()-> new UserNotFoundException(userID));
        Comment comment = this.commentRepository.findById(commentID).orElseThrow(()-> new CommentNotFoundException(commentID));
        if(!comment.getDownVotedBy().contains(user)){
            int downVotes = comment.getDownvotes()+1;
            comment.getDownVotedBy().add(user);
            comment.setDownvotes(downVotes);
            this.commentRepository.save(comment);
            return true;
        }
        return false;
    }

}
