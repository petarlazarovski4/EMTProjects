package com.marco.scmexc.models.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(schema = "public", name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description",columnDefinition="TEXT")
    private String description;

    @Column(name = "up_votes")
    private Integer upvotes;

    @Column(name = "down_votes")
    private Integer downvotes;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private SmxUser createdBy;

    @Column(name = "date_posted")
    private ZonedDateTime datePosted;

    @ManyToOne
    @JoinColumn(name = "material_id")
    @JsonIgnore
    private Material material;

    @ManyToMany
    @JoinTable(
            name = "upVotedComments",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<SmxUser> upVotedBy;

    @ManyToMany
    @JoinTable(
            name = "downVotedComments",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<SmxUser> downVotedBy;


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

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }

    public SmxUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SmxUser createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(ZonedDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Set<SmxUser> getDownVotedBy() {
        return downVotedBy;
    }

    public Set<SmxUser> getUpVotedBy() {
        return upVotedBy;
    }
}
