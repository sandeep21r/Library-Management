package com.LibraryApi.LibraryManagement.Entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "book_instances")
@EntityListeners(AuditingEntityListener.class)
public class BookInstanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BooksEntity booksEntity;

    @Column(nullable = false)
    private boolean available;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedTime = LocalDateTime.now();


    public BookInstanceEntity(BooksEntity booksEntity, boolean available) {
        this.booksEntity = booksEntity;
        this.available = available;
    }

    public BookInstanceEntity() {

    }

    public BooksEntity getBooksEntity() {
        return booksEntity;
    }

    public void setBooksEntity(BooksEntity booksEntity) {
        this.booksEntity = booksEntity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
