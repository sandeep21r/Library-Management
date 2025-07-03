package com.LibraryApi.LibraryManagement.Service;

import com.LibraryApi.LibraryManagement.Entity.TenantEntity;
import com.LibraryApi.LibraryManagement.Entity.UserEntity;
import com.LibraryApi.LibraryManagement.Exception.CustomException;
import com.LibraryApi.LibraryManagement.Repository.TenantRepository;
import com.LibraryApi.LibraryManagement.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class FetchTokenClaimService {

    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(FetchTokenClaimService.class);

    public Map<String,String> FetchTokenClaim(){
        log.info("Fetching claim from the token");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getPrincipal().toString();

        Map<?, ?> details = (Map<?, ?>) auth.getDetails();

        String userId = details.get("user_id").toString();

        String tenantId = details.get("tenant_id").toString();
        String role = details.get("role").toString();
        Map<String,String> tokenClaims = new HashMap<>();
        TenantEntity tenantEntity= tenantRepository.findById(UUID.fromString(tenantId)).orElse(null);
        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
        if(userEntity == null){
            log.error("user not exists user_id: {}",userId);
            throw new CustomException("please re login with the registered user", HttpStatus.CONFLICT);
        }
        if(tenantEntity == null){
            log.error("tenant not exists tenant_id: {}",tenantId);
            throw new CustomException("please re login with the tenant registration code", HttpStatus.CONFLICT);
        }
        tokenClaims.put("username",username);
        tokenClaims.put("userEntity",userId);
        tokenClaims.put("tenantEntity",tenantId);
        tokenClaims.put("role",role);
        return tokenClaims;
    }
}
