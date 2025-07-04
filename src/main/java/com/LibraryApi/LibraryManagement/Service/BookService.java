package com.LibraryApi.LibraryManagement.Service;


import com.LibraryApi.LibraryManagement.Entity.BookInstanceEntity;
import com.LibraryApi.LibraryManagement.Entity.BooksEntity;
import com.LibraryApi.LibraryManagement.Entity.TenantEntity;
import com.LibraryApi.LibraryManagement.Entity.UserEntity;
import com.LibraryApi.LibraryManagement.Exception.CustomException;
import com.LibraryApi.LibraryManagement.Repository.BookInstanceRepository;
import com.LibraryApi.LibraryManagement.Repository.BookRepository;
import com.LibraryApi.LibraryManagement.Request.BookDataRequest;
import com.LibraryApi.LibraryManagement.Response.BookDataResponse;
import com.LibraryApi.LibraryManagement.Response.BookInstanceResponse;
import com.LibraryApi.LibraryManagement.Response.ParentBookDataResponse;
import com.LibraryApi.LibraryManagement.Response.UserBookDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private  UserService userService;
    @Autowired
    private BookInstanceRepository bookInstanceRepository;

    @Autowired
    private FetchTokenClaimService fetchTokenClaimService;

    // Main Function
    public BooksEntity createBook(BookDataRequest bookDataRequest){
        this.fetchUserLog();
        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();
        TenantEntity tenantEntity= tenantService.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);
        UserEntity userEntity = userService.findByUsername(out.get("username"));
        this.isUserNull(userEntity,UUID.fromString(out.get("userEntity")));
        this.isTenantNull(tenantEntity,UUID.fromString(out.get("tenantEntity")));
        log.info("Creating Book with ISBN : {}" ,bookDataRequest.getIsbn());
        this.IsBookExits(tenantEntity,bookDataRequest);
        BooksEntity booksEntity = new BooksEntity(bookDataRequest.getTitle(),bookDataRequest.getAuthor(),bookDataRequest.getEdition(),bookDataRequest.getLanguage(),bookDataRequest.getPublisher(),bookDataRequest.getIsbn(),bookDataRequest.getQuantity(),tenantEntity,userEntity);
        List<BookInstanceEntity> bookInstanceEntities = booksEntity.getInstances();
        if(bookInstanceEntities == null){
            bookInstanceEntities=  new java.util.ArrayList<>();
        }
        for (int i = 1; i <= bookDataRequest.getQuantity(); i++) {
            BookInstanceEntity instance = new BookInstanceEntity();
            instance.setBooksEntity(booksEntity);
            instance.setAvailable(true);
            bookInstanceEntities.add(instance);
        }
        booksEntity.setInstances(bookInstanceEntities);
        BooksEntity booksEntity1 = bookRepository.save(booksEntity);
        log.info("Book is created isbn : {}" ,bookDataRequest.getIsbn());
        return booksEntity1;

    }
    public List<ParentBookDataResponse> getAllBook(){
        this.fetchUserLog();
        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();

        TenantEntity tenantEntity= tenantService.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);
        UserEntity userEntity = userService.findByUsername(out.get("username"));
        this.isUserNull(userEntity,UUID.fromString(out.get("userEntity")));
        this.isTenantNull(tenantEntity,UUID.fromString(out.get("tenantEntity")));

        log.info("Fetching all the book data");
        List<BooksEntity> booksEntities = bookRepository.findByTenantEntityIdAndDeletedFalse(tenantEntity.getId());
        if("ADMIN".equalsIgnoreCase(userEntity.getRoll()) ){
            List<ParentBookDataResponse> bookDataResponseList = booksEntities.stream().map(book -> {
                List<BookInstanceResponse> bookInstanceResponses = book.getInstances().stream().map(instance -> new BookInstanceResponse(instance.getId(), instance.isAvailable())).toList();
                return new BookDataResponse(book.getId(),book.getTitle(),book.getAuthor(),book.getEdition(),book.getLanguage(),book.getPublisher(),book.getIsbn(),book.getQuantity(),bookInstanceResponses);
            }) .map(response -> (ParentBookDataResponse) response).toList();
            if (bookDataResponseList.isEmpty()){
                log.info("Not data found of the book");
            }

            log.info("Return is the book data");
            return bookDataResponseList;
        }
        List<ParentBookDataResponse> bookDataResponseList = booksEntities.stream().map(book ->
                new UserBookDataResponse(book.getId(),book.getTitle(),book.getAuthor(),book.getEdition(),book.getLanguage(),book.getPublisher(),book.getIsbn(),book.getQuantity())) .map(response -> (ParentBookDataResponse) response).toList();
        if (bookDataResponseList.isEmpty()){
            log.info("Not data found of the book");
        }

        log.info("Return the book data");
        return bookDataResponseList;

    }
    public List<ParentBookDataResponse> getBookByTitle(String title){
        this.fetchUserLog();
        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();
        TenantEntity tenantEntity= tenantService.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);
        UserEntity userEntity = userService.findByUsername(out.get("username"));
        this.isUserNull(userEntity,UUID.fromString(out.get("userEntity")));
        this.isTenantNull(tenantEntity,UUID.fromString(out.get("tenantEntity")));
        log.info("Fetching all the book data using title : {}",title);

        List<BooksEntity> booksEntities = bookRepository.findByTenantEntityIdAndTitleAndDeletedFalse(tenantEntity.getId(),title);

        if("ADMIN".equalsIgnoreCase(userEntity.getRoll()) ){
            List<ParentBookDataResponse> bookDataResponseList = booksEntities.stream().map(book -> {
                List<BookInstanceResponse> bookInstanceResponses = book.getInstances().stream().map(instance -> new BookInstanceResponse(instance.getId(), instance.isAvailable())).toList();
                return new BookDataResponse(book.getId(),book.getTitle(),book.getAuthor(),book.getEdition(),book.getLanguage(),book.getPublisher(),book.getIsbn(),book.getQuantity(),bookInstanceResponses);
            }) .map(response -> (ParentBookDataResponse) response).toList();
            if (bookDataResponseList.isEmpty()){
                log.info("Not data found of the book with title : {}",title);
            }

            log.info("Return the book data with the title : {}",title);
            return bookDataResponseList;
        }
        List<ParentBookDataResponse> bookDataResponseList = booksEntities.stream().map(book ->
                new UserBookDataResponse(book.getId(),book.getTitle(),book.getAuthor(),book.getEdition(),book.getLanguage(),book.getPublisher(),book.getIsbn(),book.getQuantity())).map(response -> (ParentBookDataResponse) response).toList();
        if (bookDataResponseList.isEmpty()){
            log.info("Not data found of the book");
        }

        log.info("Return the book data");
        return bookDataResponseList;

    }
    public List<ParentBookDataResponse> getBookByAuthor(String author){
        this.fetchUserLog();
        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();

        TenantEntity tenantEntity= tenantService.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);
        UserEntity userEntity = userService.findByUsername(out.get("username"));
        this.isUserNull(userEntity,UUID.fromString(out.get("userEntity")));
        this.isTenantNull(tenantEntity,UUID.fromString(out.get("tenantEntity")));

        log.info("Fetching all the book data using author : {}",author);
        List<BooksEntity> booksEntities = bookRepository.findByTenantEntityIdAndAuthorAndDeletedFalse(tenantEntity.getId(),author);
        if("ADMIN".equalsIgnoreCase(userEntity.getRoll()) ){
            List<ParentBookDataResponse> bookDataResponseList = booksEntities.stream().map(book -> {
                List<BookInstanceResponse> bookInstanceResponses = book.getInstances().stream().map(instance -> new BookInstanceResponse(instance.getId(), instance.isAvailable())).toList();
                return new BookDataResponse(book.getId(),book.getTitle(),book.getAuthor(),book.getEdition(),book.getLanguage(),book.getPublisher(),book.getIsbn(),book.getQuantity(),bookInstanceResponses);
            }).map(response -> (ParentBookDataResponse) response).toList();
            if (bookDataResponseList.isEmpty()){
                log.info("Not data found of the book with title : {}",author);
            }

            log.info("Return the book data with the title : {}",author);
            return bookDataResponseList;
        }
        List<ParentBookDataResponse> bookDataResponseList = booksEntities.stream().map(book ->
                new UserBookDataResponse(book.getId(),book.getTitle(),book.getAuthor(),book.getEdition(),book.getLanguage(),book.getPublisher(),book.getIsbn(),book.getQuantity())).map(response -> (ParentBookDataResponse) response).toList();
        if (bookDataResponseList.isEmpty()){
            log.info("Not data found of the book");
        }

        log.info("Return the book data");
        return bookDataResponseList;
    }
    public List<ParentBookDataResponse> getBookByIsbn(String isbn){
        this.fetchUserLog();
        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();
        TenantEntity tenantEntity= tenantService.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);
        UserEntity userEntity = userService.findByUsername(out.get("username"));
        this.isUserNull(userEntity,UUID.fromString(out.get("userEntity")));
        this.isTenantNull(tenantEntity,UUID.fromString(out.get("tenantEntity")));
        log.info("Fetching all the book data using isbn : {}",isbn);
        List<BooksEntity> booksEntities = bookRepository.findByTenantEntityIdAndIsbnAndDeletedFalse(tenantEntity.getId(),isbn);
        if("ADMIN".equalsIgnoreCase(userEntity.getRoll()) ){
            List<ParentBookDataResponse> bookDataResponseList = booksEntities.stream().map(book -> {
                List<BookInstanceResponse> bookInstanceResponses = book.getInstances().stream().map(instance -> new BookInstanceResponse(instance.getId(), instance.isAvailable())).toList();
                return new BookDataResponse(book.getId(),book.getTitle(),book.getAuthor(),book.getEdition(),book.getLanguage(),book.getPublisher(),book.getIsbn(),book.getQuantity(),bookInstanceResponses);
            }).map(response -> (ParentBookDataResponse) response).toList();
            if (bookDataResponseList.isEmpty()){
                log.info("Not data found of the book with title : {}",isbn);
            }
            log.info("Return the book data with the title : {}",isbn);
            return bookDataResponseList;
        }
        List<ParentBookDataResponse> bookDataResponseList = booksEntities.stream().map(book ->
                new UserBookDataResponse(book.getId(),book.getTitle(),book.getAuthor(),book.getEdition(),book.getLanguage(),book.getPublisher(),book.getIsbn(),book.getQuantity())).map(response -> (ParentBookDataResponse) response).toList();
        if (bookDataResponseList.isEmpty()){
            log.info("Not data found of the book");
        }
        log.info("Return the book data");
        return bookDataResponseList;
    }
    public ParentBookDataResponse getBookById(UUID id){
        this.fetchUserLog();
        Map<String,String> out = fetchTokenClaimService.FetchTokenClaim();
        TenantEntity tenantEntity= tenantService.getTenatById(UUID.fromString(out.get("tenantEntity"))).orElse(null);
        UserEntity userEntity = userService.findByUsername(out.get("username"));
        this.isUserNull(userEntity,UUID.fromString(out.get("userEntity")));
        this.isTenantNull(tenantEntity,UUID.fromString(out.get("tenantEntity")));
        log.info("Fetching all the book data using id : {}",id);
        BooksEntity booksEntities = bookRepository.findById(id).orElse(null);
        if("ADMIN".equalsIgnoreCase(userEntity.getRoll()) ) {
            List<BookInstanceResponse> bookInstanceResponseList = new java.util.ArrayList<>();
            for (BookInstanceEntity instance : booksEntities.getInstances()) {
                BookInstanceResponse bookInstanceResponse = new BookInstanceResponse(instance.getId(), instance.isAvailable());
                bookInstanceResponseList.add(bookInstanceResponse);
            }
            return new BookDataResponse(booksEntities.getId(), booksEntities.getTitle(), booksEntities.getAuthor(), booksEntities.getEdition(), booksEntities.getLanguage(), booksEntities.getPublisher(), booksEntities.getIsbn(), booksEntities.getQuantity(), bookInstanceResponseList);
        }
        return new UserBookDataResponse(booksEntities.getId(),booksEntities.getTitle(),booksEntities.getAuthor(),booksEntities.getEdition(),booksEntities.getLanguage(),booksEntities.getPublisher(),booksEntities.getIsbn(),booksEntities.getQuantity());
    }


    // Helper Function
    public void fetchUserLog(){
        log.info("Fetching the user details from the access token");
    }
    public void isUserNull(UserEntity userEntity,UUID user_id){
        if(userEntity == null){
            log.error("user not exists user_id: {}",user_id);
            throw new CustomException("please re login with the registered user", HttpStatus.CONFLICT);
        }
    }
    public void isTenantNull(TenantEntity tenantEntity,UUID tenant_id){
        if(tenantEntity == null){
            log.error("tenant not exists tenant_id: {}",tenant_id);
            throw new CustomException("please re login with the tenant registration code", HttpStatus.CONFLICT);
        }
    }
    public void IsBookExits(TenantEntity tenantEntity,BookDataRequest bookDataRequest){
        if(bookRepository.existsByTenantEntityIdAndIsbnAndDeletedFalse(tenantEntity.getId(),bookDataRequest.getIsbn())){
            log.error("Book is already exists : {}",bookDataRequest.getIsbn());
            throw new CustomException("Book Already Exists with the given isbn number", HttpStatus.CONFLICT);
        }
    }
    public int  DecrementBookQuantity(UUID bookId){
        return bookRepository.decrementQuantityIfAvailable(bookId);
    }
    public int  IncrementBookQuantity(UUID bookId){
        return bookRepository.IncrementQuantityIfAvailable(bookId);
    }
//    public BooksEntity findBookByIdAndByDeleted(UUID bookId){
//        return bookRepository.findByIdAndDeletedFalse(bookId);
//    }
}
