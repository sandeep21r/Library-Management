package com.LibraryApi.LibraryManagement.Service;

import com.LibraryApi.LibraryManagement.Entity.*;
import com.LibraryApi.LibraryManagement.Exception.CustomException;
import com.LibraryApi.LibraryManagement.Repository.BookInstanceRepository;
import com.LibraryApi.LibraryManagement.Repository.BookRepository;
import com.LibraryApi.LibraryManagement.Repository.BorrowRepository;
import com.LibraryApi.LibraryManagement.Request.BorrowRequest;
import com.LibraryApi.LibraryManagement.Response.BookDataResponse;
import com.LibraryApi.LibraryManagement.Response.BorrowBookResponse;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BorrowService {

    private static final Logger log = LoggerFactory.getLogger(BorrowService.class);

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private TenantService tenantService;

    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookInstanceRepository bookInstanceRepository;
    @Autowired
    private FetchTokenClaimService fetchTokenClaimService;

    @Transactional
    public BorrowBookResponse borrowBook(UUID id){
        log.info("Fetching the user details from the access token");
        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();

        TenantEntity tenantEntity= tenantService.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);
        UserEntity userEntity = userService.findByUsername(out.get("username"));
        if(userEntity == null){
            log.error("user not exists user_id: {}",out.get("user_id"));
            throw new CustomException("please re login with the registered user", HttpStatus.CONFLICT);
        }
        if(tenantEntity == null){
            log.error("tenant not exists tenant_id: {}",out.get("tenant_id"));
            throw new CustomException("please re login with the tenant registration code", HttpStatus.CONFLICT);
        }

        log.info("User with the username {} is requesting book of id {} with associated tenant : {} ",userEntity.getUsername(),id,tenantEntity.getId());
        BorrowEntity borrow = borrowRepository.findByUserEntity_idAndBooksEntity_idAndReturned(userEntity.getId(),id,false);


        if(borrow != null){
            log.info("user already have the book");
            throw new CustomException("User Already have the book",HttpStatus.BAD_REQUEST);
        }
        BooksEntity booksEntity = bookRepository.findByIdForUpdate(id);
        if(booksEntity == null){
            log.info("No Book data exists");
            throw new CustomException("No Book data exists", HttpStatus.NOT_FOUND);

        }
        if(booksEntity.getQuantity() <= 0){
            log.info("No Book Available");
            throw new CustomException("Book is not available please try later", HttpStatus.NOT_FOUND);
        }

        BookInstanceEntity bookInstanceEntity =booksEntity.getInstances().stream().filter(BookInstanceEntity::isAvailable).findFirst().orElse(null);
        if(bookInstanceEntity == null){
            log.info("No Book data exists");
            throw new CustomException("No Book data exists", HttpStatus.NOT_FOUND);
        }

        bookInstanceEntity.setAvailable(false);
        bookService.DecrementBookQuantity(id);
        LocalDateTime now = LocalDateTime.now();
        BorrowEntity borrowEntity = new BorrowEntity(userEntity,tenantEntity,booksEntity,bookInstanceEntity,false,now.plusDays(12));
        BorrowEntity borrow1 = borrowRepository.save(borrowEntity);
        BorrowBookResponse borrowBookResponse = new BorrowBookResponse(borrow1.getId(),borrow1.getUserEntity().getId(),borrow1.getUserEntity().getUsername(),tenantEntity.getId(),bookInstanceEntity.getId(),tenantEntity.getRegistrationCode(),booksEntity.getId(),booksEntity.getTitle(),booksEntity.getAuthor(),booksEntity.getPublisher(),booksEntity.getIsbn(),borrow1.getDue_date(),borrow1.getReturned_date(),borrow1.isReturned());
        log.info("Granted Book to user");
        return borrowBookResponse;
    }

    public List<BorrowBookResponse> detailForUser(){
        log.info("Fetching the user details from the access token");
        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();

        TenantEntity tenantEntity= tenantService.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);
        UserEntity userEntity = userService.findByUsername(out.get("username"));
        if(userEntity == null){
            log.error("user not exists user_id: {}",out.get("user_id"));
            throw new CustomException("please re login with the registered user", HttpStatus.CONFLICT);
        }
        if(tenantEntity == null){
            log.error("tenant not exists tenant_id: {}",out.get("tenant_id"));
            throw new CustomException("please re login with the tenant registration code", HttpStatus.CONFLICT);
        }


        log.info("fetching the book borrowed by the user : {} with the tenant : {}",userEntity.getId(),tenantEntity.getId());
        if("ADMIN".equalsIgnoreCase(userEntity.getRoll()) ){
            return borrowRepository
                    .findByTenantEntity_id(tenantEntity.getId())
                    .stream()
                    .map(b -> new BorrowBookResponse(b.getId()
                            ,b.getUserEntity().getId(),
                            b.getUserEntity().getUsername(),
                            b.getTenantEntity().getId(),b.getBookInstanceEntity().getId(),
                            b.getTenantEntity().getRegistrationCode(),
                            b.getBooksEntity().getId(),b.getBooksEntity().getTitle(),
                            b.getBooksEntity().getAuthor(),
                            b.getBooksEntity().getPublisher(),
                            b.getBooksEntity().getIsbn(),
                            b.getDue_date(),b.getReturned_date(),
                            b.isReturned()

                    )).toList();
        }
           return borrowRepository
                .findByUserEntity_idAndTenantEntity_id(userEntity.getId(),tenantEntity.getId())
                .stream()
                .map(b -> new BorrowBookResponse(b.getId()
                        ,b.getUserEntity().getId(),
                        b.getUserEntity().getUsername(),
                        b.getTenantEntity().getId(),b.getBookInstanceEntity().getId(),
                        b.getTenantEntity().getRegistrationCode(),
                        b.getBooksEntity().getId(),b.getBooksEntity().getTitle(),
                        b.getBooksEntity().getAuthor(),
                        b.getBooksEntity().getPublisher(),
                        b.getBooksEntity().getIsbn(),
                        b.getDue_date(),b.getReturned_date(),
                        b.isReturned()

                )).toList();

    }

    @Transactional
    public Boolean returnedBook(UUID id,UUID book_instance_id){
        log.info("fetching the borrow row");
        log.info("Fetching the user details from the access token");
        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();

        TenantEntity tenantEntity= tenantService.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);
        UserEntity userEntity = userService.findByUsername(out.get("username"));
        if(userEntity == null){
            log.error("user not exists user_id: {}",out.get("user_id"));
            throw new CustomException("please re login with the registered user", HttpStatus.CONFLICT);
        }
        if(tenantEntity == null){
            log.error("tenant not exists tenant_id: {}",out.get("tenant_id"));
            throw new CustomException("please re login with the tenant registration code", HttpStatus.CONFLICT);
        }

        BorrowEntity borrowEntity=borrowRepository.findByUserEntity_idAndBooksEntity_idAndReturned(userEntity.getId(),id,false);
        if(borrowEntity == null  ){
            log.info("user does not  have the book");
            throw new CustomException("user does not  have the book",HttpStatus.BAD_REQUEST);
        }
        BooksEntity booksEntity = bookRepository.findByIdForUpdate(id);
        if(booksEntity == null){
            log.info("No Book data exists");
            throw new CustomException("No Book data exists", HttpStatus.NOT_FOUND);
        }

        BookInstanceEntity bookInstanceEntity =bookInstanceRepository.findById(book_instance_id).orElse(null);
        if(bookInstanceEntity == null){
            log.info("No Book  exists");
            throw new CustomException("No Book  exists", HttpStatus.NOT_FOUND);
        }
        bookService.IncrementBookQuantity(booksEntity.getId());
        bookInstanceEntity.setAvailable(true);
        borrowEntity.setReturned(true);
        borrowEntity.setReturned_date(LocalDateTime.now());

        return true;
    }
}
