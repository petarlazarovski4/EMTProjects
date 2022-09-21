package com.marco.scmexc.models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "material_id")
    @JsonIgnore
    private Material material;

    @OneToOne
    @JoinColumn(name = "question_id", nullable = true)
    private Question question;

    @OneToOne
    @JoinColumn(name = "file_id",nullable = true)
    private SmxFile smxFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public SmxFile getSmxFile() {
        return smxFile;
    }

    public void setSmxFile(SmxFile smxFile) {
        this.smxFile = smxFile;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
