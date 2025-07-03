package com.LibraryApi.LibraryManagement.NotUsed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleTenantEntity, UUID> {

    UserRoleTenantEntity findByUserEntity_idAndTenantEntity_id(UUID userId, UUID tenantId);

    boolean existsByUserEntity_UsernameAndTenantEntity_RegistrationCode(String username, String registrationCode);

    @Query("select u.role from UserRoleTenantEntity u where u.userEntity.id = :userId AND u.tenantEntity.id  = :tenantId")
    String findRoleByUserIdAndTenantId(@Param("userId") UUID user_id,@Param("tenantId") UUID tenant_id);


    @Query("select u from UserRoleTenantEntity u where u.userEntity.id != :userid AND u.role <> 'superadmin' AND u.tenantEntity.id = :tenantId")
    List<UserRoleTenantEntity> findUserByAdminRole(@Param("userid") UUID userId,@Param("tenantId") UUID tenant_id);


    @Query("select u from UserRoleTenantEntity u where u.role <> 'superadmin'")
    List<UserRoleTenantEntity> findUserTenantExceptSuperAdmin();

    @Query("select u from UserRoleTenantEntity u where u.userEntity.id = :userid  AND u.tenantEntity.id = :tenantId")
    UserRoleTenantEntity findUserTenantByUserIdAndLoginTenantId(@Param("userid") UUID userId,@Param("tenantId") UUID tenant_id);

    @Query("select u from UserRoleTenantEntity u where u.userEntity.id = :userid ")
    UserRoleTenantEntity findByUserId(@Param("userid") UUID userId);
}
