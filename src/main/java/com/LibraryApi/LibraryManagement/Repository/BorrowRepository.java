package com.LibraryApi.LibraryManagement.Repository;

import com.LibraryApi.LibraryManagement.Entity.BooksEntity;
import com.LibraryApi.LibraryManagement.Entity.BorrowEntity;
import com.LibraryApi.LibraryManagement.Response.BookDataResponse;
import com.LibraryApi.LibraryManagement.Response.BorrowBookResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BorrowRepository extends JpaRepository<BorrowEntity, UUID> {
    List<BorrowEntity> findByUserEntity_idAndTenantEntity_id(UUID user_id, UUID tenant_id);
    List<BorrowEntity> findByTenantEntity_id(UUID tenant_id);


    BorrowEntity findByUserEntity_idAndBooksEntity_idAndReturned(UUID user_id,UUID tenant_id,boolean returned);
}
