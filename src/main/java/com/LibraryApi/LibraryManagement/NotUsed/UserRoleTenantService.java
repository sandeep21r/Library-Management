package com.LibraryApi.LibraryManagement.NotUsed;

import com.LibraryApi.LibraryManagement.Service.CredCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleTenantService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private CredCheckService credCheckService;
    private static final Logger log = LoggerFactory.getLogger(UserRoleTenantService.class);

//    public UserTenantResponse registerToTenant(UserTenantRequest userTenantRequest){
//        log.info("Admin/SuperAmin is registering the User with username {}  to tenant with registration code: {}",userTenantRequest.getUsername(),userTenantRequest.getRegistration_code());
//
//        Map<String,Object> out = credCheckService.checking(userTenantRequest);
//
//        UserEntity userEntity = (UserEntity) out.get("user_entity");
//        TenantEntity tenantEntity = (TenantEntity) out.get("tenant_entity");
//        UserRoleTenantEntity userRoleTenantEntity = new UserRoleTenantEntity("user",userEntity,tenantEntity);
//        userRoleRepository.save(userRoleTenantEntity);
//        UserTenantResponse userTenantResponse = new UserTenantResponse(userRoleTenantEntity.getUserEntity().getId(),userRoleTenantEntity.getTenantEntity().getId(),userRoleTenantEntity.getId(),userEntity.getUsername(),userTenantRequest.getRegistration_code(),userRoleTenantEntity.getRole());
//        log.info("User with username {} is successfully register to tenant with registration code: {}",userTenantRequest.getUsername(),userTenantRequest.getRegistration_code());
//        return userTenantResponse;
//    }
//
//    public UserRoleTenantEntity Save_user(UserRoleTenantEntity userRoleTenantEntity){
//        return userRoleRepository.save(userRoleTenantEntity);
//    }
//
//    public boolean existsByUserEntity_UsernameAndTenantEntity_RegistrationCode(String username ,String registration_code){
//        return userRoleRepository.existsByUserEntity_UsernameAndTenantEntity_RegistrationCode(username,registration_code);
//    }
//
//    public UserRoleTenantEntity findByUserEntity_idAndTenantEntity_id(UUID user_id,UUID tenant_id){
//        return userRoleRepository.findByUserEntity_idAndTenantEntity_id(user_id,tenant_id);
//    }
//    public String findRoleByUserIdAndTenantId(UUID user_Id,UUID tenant_Id){
//        return userRoleRepository.findRoleByUserIdAndTenantId(user_Id,tenant_Id);
//    }
//    public List<UserRoleTenantEntity> findUserByAdminRole(UUID user_Id,UUID tenant_Id){
//        return userRoleRepository.findUserByAdminRole( user_Id,tenant_Id);
//    }
//    public List<UserRoleTenantEntity> findUserTenantExceptSuperAdmin(){
//        return userRoleRepository.findUserTenantExceptSuperAdmin();
//    }
//
//    public UserRoleTenantEntity findUserTenantByUserIdAndLoginTenantId(UUID user_id,UUID tenant_id){
//        return userRoleRepository.findUserTenantByUserIdAndLoginTenantId(user_id,tenant_id);
//    }
//
//    public UserRoleTenantEntity findByUserId(UUID id) {
//        return userRoleRepository.findByUserId(id);
//    }
}
