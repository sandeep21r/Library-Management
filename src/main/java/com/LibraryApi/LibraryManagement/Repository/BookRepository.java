package com.LibraryApi.LibraryManagement.Repository;

import com.LibraryApi.LibraryManagement.Entity.BooksEntity;
import com.LibraryApi.LibraryManagement.Response.BookDataResponse;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;
@Repository
public interface BookRepository extends JpaRepository<BooksEntity, UUID> {


    boolean  existsByTenantEntityIdAndIsbnAndDeletedFalse(UUID tenant_id,String isbn);
    List<BooksEntity> findByTenantEntityIdAndDeletedFalse(UUID tenant_id);
    List<BooksEntity> findByTenantEntityIdAndTitleAndDeletedFalse(UUID tenant_id,String title);
    List<BooksEntity> findByTenantEntityIdAndAuthorAndDeletedFalse(UUID tenant_id,String author);
    List<BooksEntity> findByTenantEntityIdAndIsbnAndDeletedFalse(UUID tenant_id,String isbn);

    @Modifying
    @Transactional
    @Query("UPDATE  BooksEntity b SET b.quantity = b.quantity - 1 where b.id  = :bookId AND b.quantity > 0 AND b.deleted=false")
    int decrementQuantityIfAvailable(@Param("bookId") UUID bookID);
    @Modifying
    @Transactional
    @Query("UPDATE  BooksEntity b SET b.quantity = b.quantity + 1 where b.id  = :bookId AND b.deleted=false")
    int IncrementQuantityIfAvailable(@Param("bookId") UUID bookID);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(" select b from BooksEntity b where b.id = :id")
    BooksEntity findByIdForUpdate(@Param("id") UUID id);

}
