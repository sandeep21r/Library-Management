package com.LibraryApi.LibraryManagement.Response;

import java.util.UUID;

public class UserResponse {
    private UUID user_id;
    private String username;
    private String registrationCode;
    private String role;

    public UserResponse( String username,UUID user_id, String registrationCode, String role) {
        this.user_id = user_id;
        this.role = role;
        this.registrationCode = registrationCode;
        this.username = username;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserResponse() {
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
       this.username=username;
    }

    @Override
    public String toString() {
        return "UserRename{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                '}';
    }
}
