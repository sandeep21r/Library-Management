package com.LibraryApi.LibraryManagement.Service;


import com.LibraryApi.LibraryManagement.Entity.TenantEntity;
import com.LibraryApi.LibraryManagement.Entity.UserEntity;
import com.LibraryApi.LibraryManagement.Exception.CustomException;
import com.LibraryApi.LibraryManagement.NotUsed.UserRoleTenantService;
import com.LibraryApi.LibraryManagement.Repository.TenantRepository;
import com.LibraryApi.LibraryManagement.Repository.UserRepository;
import com.LibraryApi.LibraryManagement.Request.UserTenantRequest;
import com.LibraryApi.LibraryManagement.Request.UserRequest;
import com.LibraryApi.LibraryManagement.Response.UserResponse;
import com.LibraryApi.LibraryManagement.Utility.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserAndTenant_ExistsService userAndTenantExistsService;
    @Autowired
    private FetchTokenClaimService fetchTokenClaimService;

    @Autowired
    private TenantService tenantService;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserResponse registerUser(UserRequest userRequest){
        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();
        TenantEntity tenantEntity1= tenantService.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);
        UserEntity userEntity = this.findByUsername(out.get("username"));
        log.info("Superadmin/Admin is registering the User with username: {}",userRequest.getUsername());
        this.UserExitsByUsername(userRequest.getUsername());
        TenantEntity tenantEntity = tenantRepository.findByRegistrationCode(userRequest.getRegistrationCode()).orElse(null);
        this.tenantExistsByRegistrationCode(tenantEntity,userRequest.getRegistrationCode());
        if("ADMIN".equalsIgnoreCase(userEntity.getRoll()) && tenantEntity != tenantEntity1){
            log.info("You cannot register the user in this tenant because you are not admin of this tenant: {}",userRequest.getRegistrationCode());
            throw new CustomException("You cannot register the user in this tenant because you are not admin of this tenant", HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity2 = new UserEntity(userRequest.getUsername(), passwordEncoder.encode(userRequest.getPassword()) , tenantEntity,userRequest.getRole());
        UserEntity ans = userRepository.save(userEntity2);
        log.info("User Successfully register with username: {} and registration code : {}",userRequest.getUsername(),userRequest.getRegistrationCode());
        return new UserResponse(ans.getUsername(),ans.getId(),userRequest.getRegistrationCode(),userRequest.getRole());
    }

    public  void UserExitsByUsername(String username){
        if(userRepository.existsByUsername(username)){
            log.info("User Already Exists with username: {}",username);
            throw new CustomException("User Already Exists", HttpStatus.CONFLICT);
        }
    }

    public void tenantExistsByRegistrationCode(TenantEntity tenantEntity,String registrationCode){
        if(tenantEntity  == null){
            log.info("Tenant Not found code: {}",registrationCode);
            throw new CustomException("Tenant Not found", HttpStatus.NOT_FOUND);
        }
    }
    public  String login(UserTenantRequest userTenantRequest){
        log.info("User Trying to login with username: {}",userTenantRequest.getUsername());

        if(userTenantRequest.getUsername() == null || userTenantRequest.getPassword() == null || userTenantRequest.getRegistration_code() == null){
            log.info("User provided incomplete detail");
            throw  new CustomException("You missed the input",HttpStatus.BAD_REQUEST);
        }
        if(!userRepository.existsByUsernameAndTenantEntity_registrationCode(userTenantRequest.getUsername(),userTenantRequest.getRegistration_code())){
            log.warn("User with username {} is not  registered to tenant with registration code: {}",userTenantRequest.getUsername(),userTenantRequest.getRegistration_code());
            throw new CustomException("User with username "+userTenantRequest.getUsername()+" is not registered to tenant with registration code "+userTenantRequest.getRegistration_code(), HttpStatus.CONFLICT);
        }
        Map<String ,Object> out= userAndTenantExistsService.CheckUserAndTenant(userTenantRequest);
        UserEntity userEntity = (UserEntity) out.get("user_entity");
        TenantEntity tenantEntity = (TenantEntity) out.get("tenant_entity");

        String token = jwtUtil.generateToken(userEntity.getUsername(),userEntity.getId(),tenantEntity.getId(), userEntity.getRoll().toUpperCase());

        log.info("User login with username: {} with registration code {} having token {}",userTenantRequest.getUsername(),userTenantRequest.getRegistration_code(),token);

        return token;
    }
    public List<UserResponse> findAllUser(){
        log.info("Fetching claim from the token");
        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();

        TenantEntity tenantEntity= tenantService.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);

        UserEntity userEntity = this.findByUsername(out.get("username"));

        if(userEntity == null){
            log.error("user not exists user_id: {}",out.get("userEntity"));
            throw new CustomException("please re login with the registered user", HttpStatus.CONFLICT);
        }
        if(tenantEntity == null){
            log.error("tenant not exists tenant_id: {}",out.get("tenant_id"));
            throw new CustomException("please re login with the tenant registration code", HttpStatus.CONFLICT);
        }


        if("SUPERADMIN".equalsIgnoreCase(userEntity.getRoll())){
            List<UserEntity> userEntityList = userRepository.findAllExceptSuperAdmin(userEntity.getId());
            System.out.println(userEntityList);
            return userEntityList.stream()
                    .map(user -> new UserResponse(user.getUsername(), user.getId(),user.getTenantEntity().getRegistrationCode(),user.getRoll()))
                    .toList();
        }
        List<UserEntity> userEntityList =  userRepository.findAllUserInTenant(userEntity.getId(),tenantEntity.getId());
        return userEntityList.stream()
                .map(user -> new UserResponse(user.getUsername(), user.getId(),userEntity.getTenantEntity().getRegistrationCode(),userEntity.getRoll()))
                .toList();
    }
    public UserEntity findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }
    public UserResponse getUser(UUID user_id){
        log.info("Fetching claim from the token");

        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();

        TenantEntity tenantEntity= tenantService.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);

        UserEntity userEntity = this.findByUsername(out.get("username"));

        if(userEntity == null){
            log.error("user not exists user_id: {}",out.get("userEntity"));
            throw new CustomException("please re login with the registered user", HttpStatus.CONFLICT);
        }
        if(tenantEntity == null){
            log.error("tenant not exists tenant_id: {}",out.get("tenant_id"));
            throw new CustomException("please re login with the tenant registration code", HttpStatus.CONFLICT);
        }


        if("SUPERADMIN".equalsIgnoreCase(userEntity.getRoll())){
            UserEntity userEntity1 = userRepository.findById(user_id).orElse(null);
            if(userEntity1 == null){
                log.error("user not exists user_id: {}",user_id);
                throw new CustomException("user not exists", HttpStatus.NOT_FOUND);
            }
            return new UserResponse(userEntity1.getUsername(), userEntity1.getId(),userEntity1.getTenantEntity().getRegistrationCode(),userEntity1.getRoll());
        }
        UserEntity user   = userRepository.findUserByIdAndTenantById(user_id,tenantEntity.getId());
        if(user == null){
            log.error("user not exists user_id: {}",user_id);
            throw new CustomException("user not exists in your tenant", HttpStatus.NOT_FOUND);
        }
        return new UserResponse(user.getUsername(), user.getId(),user.getTenantEntity().getRegistrationCode(),user.getRoll());

    }




//    public List<UserTenantResponse> getUserTenant(){
//        log.info("Fetching claim from the token");
//
//        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();
//
//        TenantEntity tenantEntity= tenantService.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);
//
//        UserEntity userEntity = this.findByUsername(out.get("username"));
//
//        if(userEntity == null){
//            log.error("user not exists user_id: {}",out.get("userEntity"));
//            throw new CustomException("please re login with the registered user", HttpStatus.CONFLICT);
//        }
//        if(tenantEntity == null){
//            log.error("tenant not exists tenant_id: {}",out.get("tenant_id"));
//            throw new CustomException("please re login with the tenant registration code", HttpStatus.CONFLICT);
//        }
//
//        String  role = userRoleTenantService.findRoleByUserIdAndTenantId(userEntity.getId(),tenantEntity.getId());
//
//        if("SUPERADMIN".equalsIgnoreCase(role)){
//            List<UserRoleTenantEntity> userRoleTenantEntity = userRoleTenantService.findUserTenantExceptSuperAdmin();
//            return userRoleTenantEntity.stream()
//                    .map(usertenant -> new UserTenantResponse(usertenant.getUserEntity().getId(),usertenant.getTenantEntity().getId(),usertenant.getId(),usertenant.getUserEntity().getUsername(),usertenant.getTenantEntity().getRegistrationCode(),usertenant.getRole()))
//                    .toList();
//        }
//        List<UserRoleTenantEntity> userRoleTenantEntity = userRoleTenantService.findUserByAdminRole(userEntity.getId(),tenantEntity.getId());
//        return userRoleTenantEntity.stream()
//                .map(usertenant -> new UserTenantResponse(usertenant.getUserEntity().getId(),usertenant.getTenantEntity().getId(),usertenant.getId(),usertenant.getUserEntity().getUsername(),usertenant.getTenantEntity().getRegistrationCode(),usertenant.getRole()))
//                .toList();
//
//    }
//    public UserTenantResponse getUserTenantByID(UUID id){
//        log.info("Fetching claim from the token");
//
//        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();
//
//        TenantEntity tenantEntity= tenantService.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);
//
//        UserEntity userEntity = this.findByUsername(out.get("username"));
//
//        if(userEntity == null){
//            log.error("user not exists user_id: {}",out.get("userEntity"));
//            throw new CustomException("please re login with the registered user", HttpStatus.CONFLICT);
//        }
//        if(tenantEntity == null){
//            log.error("tenant not exists tenant_id: {}",out.get("tenant_id"));
//            throw new CustomException("please re login with the tenant registration code", HttpStatus.CONFLICT);
//        }
//
//        String  role = userRoleTenantService.findRoleByUserIdAndTenantId(userEntity.getId(),tenantEntity.getId());
//
//        if("SUPERADMIN".equalsIgnoreCase(role)){
//            UserRoleTenantEntity userRoleTenantEntity = userRoleTenantService.findByUserId(id);
//            if (userRoleTenantEntity == null){
//                log.error("user not exists2");
//                throw new CustomException("User not exits", HttpStatus.NOT_FOUND);
//            }
//            return new UserTenantResponse(userRoleTenantEntity.getUserEntity().getId(),userRoleTenantEntity.getTenantEntity().getId(),userRoleTenantEntity.getId(),userRoleTenantEntity.getUserEntity().getUsername(),userRoleTenantEntity.getTenantEntity().getRegistrationCode(),userRoleTenantEntity.getRole());
//        }
//        UserRoleTenantEntity userRoleTenantEntity = userRoleTenantService.findUserTenantByUserIdAndLoginTenantId(id,tenantEntity.getId());
//        if (userRoleTenantEntity == null){
//            log.error("user not exists");
//            throw new CustomException("User not exits", HttpStatus.NOT_FOUND);
//        }
//        return new UserTenantResponse(userRoleTenantEntity.getUserEntity().getId(),userRoleTenantEntity.getTenantEntity().getId(),userRoleTenantEntity.getId(),userRoleTenantEntity.getUserEntity().getUsername(),userRoleTenantEntity.getTenantEntity().getRegistrationCode(),userRoleTenantEntity.getRole());
//    }

}
