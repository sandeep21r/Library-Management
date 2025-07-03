package com.LibraryApi.LibraryManagement.Response;

import java.util.UUID;

public class tenantResponse {
    private UUID tenant_id;
    private String tenantName;
    private String location;
    private String registration_code;

    public tenantResponse(UUID tenant_id,String TenantName,String location, String registration_code){
        this.tenant_id = tenant_id;
        this.tenantName=TenantName;
        this.location=location;
        this.registration_code=registration_code;

    }
    public tenantResponse(){

    }

    public UUID getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(UUID tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegistration_code() {
        return registration_code;
    }

    public void setRegistration_code(String registration_code) {
        this.registration_code = registration_code;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String TenantName) {
        this.tenantName = TenantName;
    }
}
