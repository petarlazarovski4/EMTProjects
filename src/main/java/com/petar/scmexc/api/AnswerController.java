package com.marco.scmexc.api;

import com.marco.scmexc.models.domain.Answer;
import com.marco.scmexc.models.domain.Question;
import com.marco.scmexc.models.requests.AnswerRequest;
import com.marco.scmexc.models.response.AnswerResponse;
import com.marco.scmexc.security.CurrentUser;
import com.marco.scmexc.security.UserPrincipal;
import com.marco.scmexc.services.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/answers")
public class AnswerController {

    private final AnswerService service;

    public AnswerController(AnswerService service) {
        this.service = service;
    }

    @PostMapping("/addAnswer")
    public ResponseEntity<String> addAnswer(@CurrentUser UserPrincipal user, @RequestBody AnswerRequest request) {
        this.service.addAnswer(request, user.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/question/{questionID}")
    public ResponseEntity<List<AnswerResponse>> getAnswerByQuestion(@PathVariable Long questionID) {
        List<AnswerResponse> answerResponses = this.service.getAllByQuestion(questionID).stream()
                .map(answer -> {
                    return AnswerResponse.of(answer.getId(), answer.getQuestion().getDescription(),
                            answer.getAnswer(), answer.getUpVotes(), answer.getDownVotes(), answer.getAnsweredBy().getEmail(), answer.getDatePosted());
                }).collect(Collectors.toList());
        return ResponseEntity.ok(answerResponses);
    }

    @PostMapping("/{answerID}/upvote")
    public ResponseEntity<String> incUpVotes(@PathVariable Long answerID, @CurrentUser UserPrincipal userPrincipal) {
        boolean success = this.service.incUpVotes(answerID, userPrincipal.getId());
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Upvoted.");
    }

    @PostMapping("/{answerID}/downvote")
    public ResponseEntity<String> incDownVotes(@PathVariable Long answerID, @CurrentUser UserPrincipal userPrincipal) {
        boolean success = this.service.incDownVotes(answerID, userPrincipal.getId());
        return success ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Downvoted.");
    }

    @DeleteMapping("/{answerID}/delete")
    public AnswerResponse deleteAnswer(@PathVariable Long answerID) {
        Answer answer = this.service.deleteAnswerByID(answerID);
        return AnswerResponse.of(answer.getId(), answer.getQuestion().getDescription(), answer.getAnswer(),
                answer.getUpVotes(), answer.getDownVotes(), answer.getAnsweredBy().getEmail(), answer.getDatePosted());
    }

    @PostMapping("/edit")
    public AnswerResponse editAnswer(@RequestBody AnswerRequest request) {
        Answer answer = this.service.editAnswerByID(request);
        return AnswerResponse.of(answer.getId(), answer.getQuestion().getDescription(), answer.getAnswer(),
                answer.getUpVotes(), answer.getDownVotes(), answer.getAnsweredBy().getEmail(), answer.getDatePosted());
    }

    @GetMapping("/{answerID}")
    public AnswerResponse viewAnswer(@PathVariable Long answerID) {
        Answer answer = this.service.viewAnswerByID(answerID);
        return AnswerResponse.of(answer.getId(), answer.getQuestion().getDescription(), answer.getAnswer(),
                answer.getUpVotes(), answer.getDownVotes(), answer.getAnsweredBy().getEmail(), answer.getDatePosted());
    }

    @PostMapping("/selectAnswer")
    public AnswerResponse selectUseFullAnswer(@RequestBody AnswerRequest request) {
        Answer answer = this.service.selectUseFullAnswer(request);
        return AnswerResponse.of(answer.getId(), answer.getQuestion().getDescription(), answer.getAnswer(),
                answer.getUpVotes(), answer.getDownVotes(), answer.getAnsweredBy().getEmail(), answer.getDatePosted());
    }

    @GetMapping("/question/{questionID}/correct")
    public AnswerResponse getCorrectAnswer(@PathVariable Long questionID) {
        Answer answer = this.service.getCorrectAnswer(questionID);
        return AnswerResponse.of(answer.getId(), answer.getQuestion().getDescription(), answer.getAnswer(),
                answer.getUpVotes(), answer.getDownVotes(), answer.getAnsweredBy().getEmail(), answer.getDatePosted());
    }
}
