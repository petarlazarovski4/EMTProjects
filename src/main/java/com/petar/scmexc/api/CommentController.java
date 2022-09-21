package com.marco.scmexc.api;

import com.marco.scmexc.models.requests.CommentRequest;
import com.marco.scmexc.models.response.CommentResponse;
import com.marco.scmexc.security.CurrentUser;
import com.marco.scmexc.security.UserPrincipal;
import com.marco.scmexc.services.CommentService;
import com.marco.scmexc.web.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<CommentResponse> getAllComments(){
        return commentMapper.findAll();
    }

    @GetMapping("/all/{id}")
    public List<CommentResponse> findById(@PathVariable Long id){
        return commentMapper.findAllByMaterialId(id);
    }

    @PostMapping("/create")
    public CommentResponse createNewMaterial(@RequestBody CommentRequest request) {
        return commentMapper.save(request);
    }

    @PostMapping("/{id}/upvote")
    public ResponseEntity<String> incUpVotes(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
        boolean success = this.service.incUpVotes(id, userPrincipal.getId());
        return success ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Upvoted.");
    }

    @PostMapping("/{id}/downvote")
    public ResponseEntity<String> incDownVotes(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
        boolean success = this.service.incDownVotes(id, userPrincipal.getId());
        return success ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Downvoted.");
    }

}
