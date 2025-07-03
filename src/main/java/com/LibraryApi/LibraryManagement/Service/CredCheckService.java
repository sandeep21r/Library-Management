package com.LibraryApi.LibraryManagement.Service;

import com.LibraryApi.LibraryManagement.Exception.CustomException;
import com.LibraryApi.LibraryManagement.NotUsed.UserRoleRepository;
import com.LibraryApi.LibraryManagement.Repository.UserRepository;
import com.LibraryApi.LibraryManagement.Request.UserTenantRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class CredCheckService {

   @Autowired
   private UserRepository userRepository;
   @Autowired
   private UserAndTenant_ExistsService userAndTenantExistsService;

    private static final Logger log = LoggerFactory.getLogger(CredCheckService.class);


    public  Map<String,Object> checking(UserTenantRequest userTenantRequest){
        Map<String , Object> out = new HashMap<>();
        if(userTenantRequest.getUsername() == null ||  userTenantRequest.getRegistration_code() == null){
            log.info("User provided incomplete detail");
            throw  new CustomException("Any input is empty",HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByUsernameAndTenantEntity_registrationCode(userTenantRequest.getUsername(),userTenantRequest.getRegistration_code())){
            log.warn("User with username {} is already registering to tenant with registration code: {}",userTenantRequest.getUsername(),userTenantRequest.getRegistration_code());
            throw new CustomException("User with username "+userTenantRequest.getUsername()+" is already registering to tenant with registration code "+userTenantRequest.getRegistration_code(), HttpStatus.CONFLICT);
        }

        return userAndTenantExistsService.CheckUserAndTenant(userTenantRequest);

    }
}
