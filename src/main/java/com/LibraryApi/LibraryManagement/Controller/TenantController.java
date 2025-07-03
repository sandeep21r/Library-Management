package com.LibraryApi.LibraryManagement.Controller;


import com.LibraryApi.LibraryManagement.Entity.TenantEntity;
import com.LibraryApi.LibraryManagement.Mapper.TenantMapper;
import com.LibraryApi.LibraryManagement.Response.APIResponse;
import com.LibraryApi.LibraryManagement.Request.tenantRequest;
import com.LibraryApi.LibraryManagement.Response.UserTenantResponse;
import com.LibraryApi.LibraryManagement.Response.tenantResponse;
import com.LibraryApi.LibraryManagement.Service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/tenant")
public class TenantController {


    @Autowired
    private TenantService tenantService;


    private static final Logger log = LoggerFactory.getLogger(TenantController.class);

    @PostMapping("/registerTenant")
    public ResponseEntity<APIResponse<tenantResponse>> registerTenant(@RequestBody tenantRequest request) {
        tenantResponse tenantResponse = tenantService.registerTenant(request);
        APIResponse<tenantResponse> schoolResponseAPIResponse = new APIResponse<>(true,"Tenant created successfully", tenantResponse);
        return new ResponseEntity<>(schoolResponseAPIResponse,HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public  ResponseEntity<APIResponse<List<tenantResponse>>> getAllTenants(){
        List<TenantEntity> ListSchoolResponse = tenantService.getAllTenants();
        String message = ListSchoolResponse.isEmpty() ? "No Tenant Registered yet" : "Tenant Fetched Successfully";
        List<tenantResponse> allTenantResponse =    ListSchoolResponse.stream().map(TenantMapper::toResponse).toList();
        APIResponse<List<tenantResponse>> responses = new APIResponse<>(true,message, allTenantResponse);
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }

    @GetMapping("/myTenant")
    public ResponseEntity<APIResponse<tenantResponse>> getMyTenant(){
        TenantEntity tenantEntity = tenantService.myTenant( );

         tenantResponse tenantResponse = new tenantResponse(tenantEntity.getId(),tenantEntity.getTenantName(),tenantEntity.getLocation(),tenantEntity.getRegistrationCode());
         APIResponse<tenantResponse> responses = new APIResponse<>(true,"Tenant Fetched Successfully", tenantResponse);

        return new ResponseEntity<>(responses,HttpStatus.FOUND);
    }
}
