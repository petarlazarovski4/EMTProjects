package com.marco.scmexc.models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "public", name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", columnDefinition="TEXT")
    private String description;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<Answer> answers;

    @OneToOne(mappedBy = "question")
    @JsonIgnore
    private Item item;

    @OneToOne
    @JoinColumn(name = "answer_id", nullable = true)
    private Answer usefulAnswer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Answer getUsefulAnswer() {
        return usefulAnswer;
    }

    public void setUsefulAnswer(Answer usefulAnswer) {
        this.usefulAnswer = usefulAnswer;
    }
}
