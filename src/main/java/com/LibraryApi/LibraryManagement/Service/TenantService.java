package com.LibraryApi.LibraryManagement.Service;


import com.LibraryApi.LibraryManagement.Entity.TenantEntity;
import com.LibraryApi.LibraryManagement.Entity.UserEntity;
import com.LibraryApi.LibraryManagement.Exception.CustomException;
import com.LibraryApi.LibraryManagement.Repository.TenantRepository;
import com.LibraryApi.LibraryManagement.Repository.UserRepository;
import com.LibraryApi.LibraryManagement.Request.tenantRequest;
import com.LibraryApi.LibraryManagement.Response.tenantResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FetchTokenClaimService fetchTokenClaimService;


    private static final Logger log = LoggerFactory.getLogger(TenantService.class);

    public tenantResponse registerTenant(tenantRequest request){
        log.info("SuperAdmin is Attempting to create Tenant with TenantName: {} and Location: {}",request.getTenantName(), request.getLocation());
        log.info("finding the user who will be the admin of the tenant : {}" ,request.getUsername());
        if(userRepository.existsByUsername(request.getUsername())){
            log.info("Please create different user it already exits");
            throw new CustomException("User is already exists and register in any other tenant please use different username",HttpStatus.NOT_FOUND);
        }
        String registration_code = request.getTenantName()+"-"+request.getLocation();
        if (tenantRepo.existsByRegistrationCode(registration_code)) {
            log.error("Duplicate Code Found: {}", registration_code);
            throw new CustomException("Registration code " + registration_code + " already exists", HttpStatus.CONFLICT);
        }

        TenantEntity tenant = new TenantEntity(request.getTenantName(),request.getLocation(),registration_code);
        TenantEntity tenantEntity = tenantRepo.save(tenant);
        log.info("Adding the user to the user  table username : {} and registration code : {}",request.getUsername(),registration_code);
        UserEntity userEntity = new UserEntity(request.getUsername(), passwordEncoder.encode(request.getPassword()) ,tenantEntity,"Admin");
        userRepository.save(userEntity);
        log.info("Successfully created Tenant with id: {}", tenantEntity.getId());
        return new tenantResponse(tenantEntity.getId(),request.getTenantName(), request.getLocation(),registration_code);
    }


    public List<TenantEntity> getAllTenants() {
        log.info("Fetching All Tenants");
        List<TenantEntity> tenantEntities = tenantRepo.findAll();
        if (tenantEntities.isEmpty()) {
            log.info("No Tenants Data Exits");
        } else {
            log.info("Fetched All Tenants");
        }

        return tenantEntities;
    }

    public TenantEntity myTenant(){
        log.info("Fetching claim from the token");

        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();

        TenantEntity tenantEntity= this.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);

        UserEntity userEntity = userRepository.findByUsername(out.get("username")).orElse(null);

        if(userEntity == null){
            log.error("user not exists user_id: {}",out.get("userEntity"));
            throw new CustomException("please re login with the registered user", HttpStatus.CONFLICT);
        }

        if (tenantEntity == null) {
            log.info("No Tenant Data Exits");
        } else {
            log.info("Fetched Tenants Data");
        }
        return tenantEntity;
    }
    public Optional<TenantEntity> getTenatById(UUID tenant_id){
        return tenantRepo.findById(tenant_id);
    }
}
