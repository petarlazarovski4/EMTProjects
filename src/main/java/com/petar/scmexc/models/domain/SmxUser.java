package com.marco.scmexc.models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "public", name = "users")
public class SmxUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_created")
    private ZonedDateTime date_created;

    @Column(name = "is_activated")
    private boolean activated;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role")
    private Role role;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "course_moderator",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> moderatingCourses = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "upVotedBy")
    private Set<Answer> upVotedFor;

    @JsonIgnore
    @ManyToMany(mappedBy = "downVotedBy")
    private Set<Answer> downVotedFor;

    @JsonIgnore
    @ManyToMany(mappedBy = "upVotedBy")
    private Set<Material> materialsUpVotedFor;

    @JsonIgnore
    @ManyToMany(mappedBy = "downVotedBy")
    private Set<Material> materialsDownVotedFor;

    @JsonIgnore
    @ManyToMany(mappedBy = "upVotedBy")
    private Set<Comment> commentsUpVotedFor;

    @JsonIgnore
    @ManyToMany(mappedBy = "downVotedBy")
    private Set<Comment> commentsDownVotedFor;

    public Set<Course> getModeratingCourses() {
        return moderatingCourses;
    }

    public void setModeratingCourses(Set<Course> moderatingCourses) {
        this.moderatingCourses = moderatingCourses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ZonedDateTime getDate_created() {
        return date_created;
    }

    public void setDate_created(ZonedDateTime date_created) {
        this.date_created = date_created;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Material> getMaterialsDownVotedFor() {
        return materialsDownVotedFor;
    }

    public Set<Material> getMaterialsUpVotedFor() {
        return materialsUpVotedFor;
    }

    public void setMaterialsDownVotedFor(Set<Material> materialsDownVotedFor) {
        this.materialsDownVotedFor = materialsDownVotedFor;
    }

    public void setUpVotedFor(Set<Answer> upVotedFor) {
        this.upVotedFor = upVotedFor;
    }

    public void setMaterialsUpVotedFor(Set<Material> materialsUpVotedFor) {
        this.materialsUpVotedFor = materialsUpVotedFor;
    }

    public void setDownVotedFor(Set<Answer> downVotedFor) {
        this.downVotedFor = downVotedFor;
    }
}
