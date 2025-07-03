package com.LibraryApi.LibraryManagement.Request;

import com.LibraryApi.LibraryManagement.Entity.TenantEntity;
import jakarta.servlet.Registration;

public class UserRequest {
    private String username;
    private String password;
    private String  registrationCode;
    private String role;

    public UserRequest(String username, String role, String registrationCode, String password) {
        this.username = username;
        this.role = role;
        this.registrationCode = registrationCode;
        this.password = password;
    }

    public UserRequest() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
       this.username=username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "Username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
