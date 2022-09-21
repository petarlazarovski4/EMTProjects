package com.marco.scmexc.models.domain;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(schema = "public",name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer", columnDefinition="TEXT")
    private String answer;

    @Column(name = "upVotes")
    private int upVotes;

    @Column(name = "downVotes")
    private int downVotes;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "answered_by")
    private SmxUser answeredBy;

    @ManyToMany
    @JoinTable(
            name = "upVotedAnswers",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<SmxUser> upVotedBy;

    @ManyToMany
    @JoinTable(
            name = "downVotedAnswers",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<SmxUser> downVotedBy;

    @Column(name = "date_answered")
    private ZonedDateTime datePosted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public SmxUser getAnsweredBy() {
        return answeredBy;
    }

    public void setAnsweredBy(SmxUser answeredBy) {
        this.answeredBy = answeredBy;
    }

    public ZonedDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(ZonedDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public Set<SmxUser> getDownVotedBy() {
        return downVotedBy;
    }

    public Set<SmxUser> getUpVotedBy() {
        return upVotedBy;
    }

    public void setUpVotedBy(Set<SmxUser> upVotedBy) {
        this.upVotedBy = upVotedBy;
    }

    public void setDownVotedBy(Set<SmxUser> downVotedBy) {
        this.downVotedBy = downVotedBy;
    }
}
