package com.LibraryApi.LibraryManagement.Entity;


import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "tenants",uniqueConstraints = {
        @UniqueConstraint(columnNames = "registration_code")
})
@EntityListeners(AuditingEntityListener.class)
public class TenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="tenant_name",nullable = false)
    private String tenantName;

    @Column(name="location",nullable = false)
    private String location;

    @Column(name="registration_code",nullable = false,unique = true)
    private String registrationCode;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version;


    public TenantEntity(){

    }

    public TenantEntity(String  tenantName,String location, String registrationCode){
        this.tenantName=tenantName;
        this.location=location;
        this.registrationCode=registrationCode;

    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String TenantName) {
        this.tenantName = TenantName;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "TenantEntity{" +
                "id=" + id +
                ", tenantName='" + tenantName + '\'' +
                ", location='" + location + '\'' +
                ", registrationCode='" + registrationCode + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", version=" + version +
                '}';
    }
}
