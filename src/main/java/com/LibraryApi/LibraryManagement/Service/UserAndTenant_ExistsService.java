package com.LibraryApi.LibraryManagement.Service;

import com.LibraryApi.LibraryManagement.Entity.TenantEntity;
import com.LibraryApi.LibraryManagement.Entity.UserEntity;
import com.LibraryApi.LibraryManagement.Exception.CustomException;
import com.LibraryApi.LibraryManagement.Repository.TenantRepository;
import com.LibraryApi.LibraryManagement.Repository.UserRepository;
import com.LibraryApi.LibraryManagement.Request.UserTenantRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserAndTenant_ExistsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TenantRepository tenantRepository;

    private static final Logger log = LoggerFactory.getLogger(UserAndTenant_ExistsService.class);

    public Map<String,Object> CheckUserAndTenant(UserTenantRequest userTenantRequest){
        Map<String , Object> out = new HashMap<>();
        UserEntity userEntity = userRepository.findByUsername(userTenantRequest.getUsername()).orElse(null);

        if(userTenantRequest.getPassword() != null){
            boolean passwordMatches = passwordEncoder.matches(userTenantRequest.getPassword(), userEntity.getPassword());
            if( !passwordMatches){
                log.warn("User with username {} send wrong password",userTenantRequest.getUsername());
                throw new CustomException("Invalid Password", HttpStatus.UNAUTHORIZED);
            }
        }

        TenantEntity tenantEntity = tenantRepository.findByRegistrationCode(userTenantRequest.getRegistration_code()).orElse(null);

        out.put("user_entity",userEntity);
        out.put("tenant_entity",tenantEntity);
        return out;
    }


}
