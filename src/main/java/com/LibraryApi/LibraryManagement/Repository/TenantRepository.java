package com.LibraryApi.LibraryManagement.Repository;

import com.LibraryApi.LibraryManagement.Entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<TenantEntity, UUID> {

    boolean existsByRegistrationCode(String registrationCode);


    Optional<TenantEntity> findByRegistrationCode(String RegistrationCode);


}
