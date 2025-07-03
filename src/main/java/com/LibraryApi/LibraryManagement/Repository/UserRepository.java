package com.LibraryApi.LibraryManagement.Repository;

import com.LibraryApi.LibraryManagement.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {


    boolean existsByUsername(String username);
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsernameAndTenantEntity_registrationCode(String Username,String registration_Code);



    @Query("Select u from UserEntity u ")
    List<UserEntity> findAllExceptSuperAdmin(@Param("user_id") UUID user_id);
    @Query("Select u from UserEntity u where u.id != :user_id AND u.role <> 'SUPERADMIN' AND u.tenantEntity.id = :tenant_id")
    List<UserEntity> findAllUserInTenant(@Param("user_id") UUID user_id,@Param("tenant_id") UUID tenant_id);
    @Query("Select u from UserEntity u where u.id = :user_id  AND u.tenantEntity.id = :tenant_id")
    UserEntity findUserByIdAndTenantById(@Param("user_id") UUID user_id,@Param("tenant_id") UUID tenant_id);

}
