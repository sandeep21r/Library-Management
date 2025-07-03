package com.LibraryApi.LibraryManagement.Request;

public class tenantRequest {

    private String tenantName;
    private String location;
    private String username;
    private String password;

    public tenantRequest(String  TenantName, String location,String username,String password){

        this.tenantName=TenantName;
        this.location=location;
        this.username= username;
        this.password = password;

    }
    public tenantRequest(){

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String TenantName) {
        this.tenantName = TenantName;
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

}
