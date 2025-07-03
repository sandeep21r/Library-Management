package com.LibraryApi.LibraryManagement.Controller;


import com.LibraryApi.LibraryManagement.Entity.BookInstanceEntity;
import com.LibraryApi.LibraryManagement.Entity.BooksEntity;
import com.LibraryApi.LibraryManagement.Exception.CustomException;
import com.LibraryApi.LibraryManagement.Request.BookDataRequest;
import com.LibraryApi.LibraryManagement.Request.BorrowRequest;
import com.LibraryApi.LibraryManagement.Request.UserTenantRequest;
import com.LibraryApi.LibraryManagement.Response.*;
import com.LibraryApi.LibraryManagement.Service.BookService;
import com.LibraryApi.LibraryManagement.Service.BorrowService;
import com.LibraryApi.LibraryManagement.Service.FetchTokenClaimService;
import org.apache.commons.collections4.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/library/book")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;
    @Autowired
    private BorrowService borrowService;


    @PostMapping("/create")
    public ResponseEntity<APIResponse<BookDataResponse>> createBook(@RequestBody BookDataRequest bookDataRequest){
        BooksEntity booksEntity  =  bookService.createBook(bookDataRequest);
        List<BookInstanceResponse> bookInstanceResponseList = new java.util.ArrayList<>();
        for(BookInstanceEntity instance : booksEntity.getInstances()){
            BookInstanceResponse bookInstanceResponse = new BookInstanceResponse(instance.getId(),instance.isAvailable());
            bookInstanceResponseList.add(bookInstanceResponse);
        }
        BookDataResponse bookDataResponse = new BookDataResponse(booksEntity.getId(),booksEntity.getTitle(),booksEntity.getAuthor(),booksEntity.getEdition(),booksEntity.getLanguage(),booksEntity.getPublisher(),booksEntity.getIsbn(),booksEntity.getQuantity(),bookInstanceResponseList);
        System.out.println(bookDataResponse);
        APIResponse<BookDataResponse> apiResponse = new APIResponse<>(true,"Book is created successfully",bookDataResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @GetMapping("/list")
    public ResponseEntity<APIResponse<List<ParentBookDataResponse>>> getAllBookAdmin(){
        List<ParentBookDataResponse> bookDataResponseList = bookService.getAllBook();
        APIResponse<List<ParentBookDataResponse>> apiResponse = new APIResponse<>(true,"Fetched Books",bookDataResponseList);
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<APIResponse<List<ParentBookDataResponse>>> getAllBookByTitle(@PathVariable("title") String title){
        List<ParentBookDataResponse> bookDataResponseList = bookService.getBookByTitle(title);
        APIResponse<List<ParentBookDataResponse>> apiResponse = new APIResponse<>(true,"Fetched Books using the title "+title,bookDataResponseList);
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);
    }
    @GetMapping("/author/{author}")
    public ResponseEntity<APIResponse<List<ParentBookDataResponse>>> getAllBookByAuthor(@PathVariable("author") String author){

        List<ParentBookDataResponse> bookDataResponseList = bookService.getBookByAuthor(author);
        APIResponse<List<ParentBookDataResponse>> apiResponse = new APIResponse<>(true,"Fetched Books using the author "+author,bookDataResponseList);
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);
    }
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<APIResponse<List<ParentBookDataResponse>>> getAllBookByIsbn(@PathVariable("isbn") String isbn){

        List<ParentBookDataResponse> bookDataResponseList = bookService.getBookByIsbn(isbn);
        APIResponse<List<ParentBookDataResponse>> apiResponse = new APIResponse<>(true,"Fetched Books using the isbn "+isbn,bookDataResponseList);
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);
    }
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ParentBookDataResponse>> getAllBookById(@PathVariable("id") UUID id){

        ParentBookDataResponse bookDataResponseList = bookService.getBookById(id);
        APIResponse<ParentBookDataResponse> apiResponse = new APIResponse<>(true,"Fetched Books using the id "+id,bookDataResponseList);
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);
    }




    @PostMapping("{book_id}/borrow")
    public ResponseEntity<APIResponse<BorrowBookResponse>>  borrowBook(@PathVariable("book_id") UUID id){
        BorrowBookResponse success = borrowService.borrowBook(id);

        APIResponse<BorrowBookResponse> apiResponse = new APIResponse<>(true,"Book is allocated to you please visit again",success);
        return new ResponseEntity<>(apiResponse,HttpStatus.ACCEPTED);
    }
    @PutMapping("{book_id}/returned/{book_instance_id}")
    public ResponseEntity<APIResponse<String>> BorrowBookReturned(@PathVariable UUID book_id,@PathVariable UUID book_instance_id){
        if(borrowService.returnedBook(book_id,book_instance_id) ){
            APIResponse<String> apiResponse = new APIResponse<>(true,"Book Returned","Thanks For Returning the Book");
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }
        throw  new CustomException("Book is not returned please try gain or something happened wrong",HttpStatus.BAD_REQUEST);


    }

    @GetMapping("/borrowBook/list")
    public ResponseEntity<APIResponse<List<BorrowBookResponse>>> BorrowBookDetail(){
        List<BorrowBookResponse> borrowBookResponseList = borrowService.detailForUser();
        APIResponse<List<BorrowBookResponse>> apiResponse = new APIResponse<>(true,"Detail Fetched",borrowBookResponseList);
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);
    }

  }

