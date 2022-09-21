package com.marco.scmexc.models.response;

public class UserResponse {
    public final Long id;
    public final String username;
    public final String firstName;
    public final String lastName;
    public final String email;
    public final String role;

    private UserResponse(Long id, String username, String firstName, String lastName, String email, String role) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public static UserResponse of(Long id, String username, String firstName, String lastName, String email, String role) {
        return new UserResponse(id, username, firstName, lastName, email, role);
    }
}
