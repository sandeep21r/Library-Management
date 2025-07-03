package com.LibraryApi.LibraryManagement.Repository;

import com.LibraryApi.LibraryManagement.Entity.BookInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookInstanceRepository extends JpaRepository<BookInstanceEntity, UUID> {
}
