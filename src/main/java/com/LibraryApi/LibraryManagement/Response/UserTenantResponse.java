package com.LibraryApi.LibraryManagement.Response;

import java.util.UUID;

public class UserTenantResponse {
    private UUID user_id;
    private UUID tenant_id;
    private UUID userTenant_Id;
    private String username;
    private String registration_code;
    private String role;
    public UserTenantResponse(UUID user_id,UUID tenant_id,String username, String registration_code,String role) {
        this.user_id = user_id;
        this.tenant_id= tenant_id;
        this.username = username;
        this.role=role;
        this.registration_code = registration_code;
    }

    public UserTenantResponse() {
    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRegistration_code() {
        return registration_code;
    }

    public void setRegistration_code(String registration_code) {
        this.registration_code = registration_code;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public UUID getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(UUID tenant_id) {
        this.tenant_id = tenant_id;
    }

    @Override
    public String toString() {
        return "UserTenantRequest{" +
                "username='" + username + '\'' +

                ", registration_code='" + registration_code + '\'' +
                '}';
    }
}
