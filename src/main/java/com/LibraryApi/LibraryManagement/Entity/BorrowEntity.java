package com.LibraryApi.LibraryManagement.Entity;


import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "borrows")
@EntityListeners(AuditingEntityListener.class)
public class BorrowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private TenantEntity tenantEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BooksEntity booksEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_instance_id", nullable = false)
    private BookInstanceEntity bookInstanceEntity;

    @Column(name="returned",nullable = false)
    private boolean returned;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime due_date;

    @Column(name = "returned_date", nullable = true)
    private LocalDateTime returned_date;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedTime = LocalDateTime.now();


    public BorrowEntity() {
    }

    public BorrowEntity(UserEntity userEntity, TenantEntity tenantEntity, BooksEntity booksEntity, BookInstanceEntity bookInstanceEntity, boolean returned, LocalDateTime due_date) {
        this.userEntity = userEntity;
        this.tenantEntity = tenantEntity;
        this.booksEntity = booksEntity;
        this.bookInstanceEntity = bookInstanceEntity;
        this.returned = returned;
        this.due_date = due_date;
    }

    public BookInstanceEntity getBookInstanceEntity() {
        return bookInstanceEntity;
    }

    public void setBookInstanceEntity(BookInstanceEntity bookInstanceEntity) {
        this.bookInstanceEntity = bookInstanceEntity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public TenantEntity getTenantEntity() {
        return tenantEntity;
    }

    public void setTenantEntity(TenantEntity tenantEntity) {
        this.tenantEntity = tenantEntity;
    }

    public BooksEntity getBooksEntity() {
        return booksEntity;
    }

    public void setBooksEntity(BooksEntity booksEntity) {
        this.booksEntity = booksEntity;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public LocalDateTime getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDateTime due_date) {
        this.due_date = due_date;
    }

    public LocalDateTime getReturned_date() {
        return returned_date;
    }

    public void setReturned_date(LocalDateTime returned_date) {
        this.returned_date = returned_date;
    }

    @Override
    public String toString() {
        return "BorrowEntity{" +
                "id=" + id +
                ", userEntity=" + userEntity +
                ", tenantEntity=" + tenantEntity +
                ", booksEntity=" + booksEntity +
                ", returned=" + returned +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
