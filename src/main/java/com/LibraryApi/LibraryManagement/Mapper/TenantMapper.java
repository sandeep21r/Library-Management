package com.LibraryApi.LibraryManagement.Mapper;

import com.LibraryApi.LibraryManagement.Entity.TenantEntity;
import com.LibraryApi.LibraryManagement.Response.tenantResponse;

public class TenantMapper {
    public static tenantResponse toResponse(TenantEntity tenantEntity){
        return new tenantResponse(tenantEntity.getId(),tenantEntity.getTenantName(), tenantEntity.getLocation(),tenantEntity.getRegistrationCode());
    }
}
