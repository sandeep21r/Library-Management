package com.LibraryApi.LibraryManagement.Service;

import com.LibraryApi.LibraryManagement.Entity.TenantEntity;
import com.LibraryApi.LibraryManagement.Entity.UserEntity;
import com.LibraryApi.LibraryManagement.Repository.TenantRepository;
import com.LibraryApi.LibraryManagement.Repository.UserRepository;
import com.LibraryApi.LibraryManagement.NotUsed.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInsert implements CommandLineRunner {


    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(DataInsert.class);
    private final PasswordEncoder passwordEncoder;
    public DataInsert(TenantRepository tenantRepository, UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;

        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Inserting the superadmin in the database");
        String registration_code = "default-001";

        TenantEntity tenantEntity = new TenantEntity();

        if(!tenantRepository.existsByRegistrationCode(registration_code)){
            System.out.println(tenantEntity);
            tenantEntity.setTenantName("default");
            tenantEntity.setLocation("001");
            tenantEntity.setRegistrationCode(registration_code);
           tenantRepository.save(tenantEntity);

        }

        TenantEntity tenant = tenantRepository.findByRegistrationCode(registration_code).orElse(null);
        String username = "default";
        UserEntity userEntity=new UserEntity();
        if(!userRepository.existsByUsername(username)){
            userEntity.setUsername(username);
            userEntity.setPassword(passwordEncoder.encode("12345"));
            userEntity.setTenantEntity(tenant);
            userEntity.setRoll("SuperAdmin");
            userRepository.save(userEntity);
        }

        log.info("Inserted the superadmin in the database");


//        String role = "superadmin";
//        if(!userRoleRepository.existsByUserEntity_UsernameAndTenantEntity_RegistrationCode(username,registration_code)){
//            TenantEntity tenant = tenantRepository.findByRegistrationCode(registration_code)
//                    .orElseThrow(() -> new RuntimeException("Tenant not found"));
//
//            UserRoleTenantEntity userRoleTenantEntity = new UserRoleTenantEntity();
//            userRoleTenantEntity.setUserEntity(userEntity1);
//            userRoleTenantEntity.setTenantEntity(tenant);
//            userRoleTenantEntity.setRole(role);
//
//            userRoleRepository.save(userRoleTenantEntity);
//        }

    }
}
