package com.LibraryApi.LibraryManagement.Request;

public class UserTenantRequest {
    private String username;
    private String password;
    private String registration_code;

    public UserTenantRequest(String username, String password, String registration_code) {
        this.username = username;
        this.password = password;
        this.registration_code = registration_code;
    }

    public UserTenantRequest() {
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

    public String getRegistration_code() {
        return registration_code;
    }

    public void setRegistration_code(String registration_code) {
        this.registration_code = registration_code;
    }

    @Override
    public String toString() {
        return "UserTenantRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", registration_code='" + registration_code + '\'' +
                '}';
    }
}
