package com.LibraryApi.LibraryManagement.Response;

import java.time.LocalDateTime;
import java.util.UUID;

public class BorrowBookResponse {
    private UUID Borrow_Book_id;
    private UUID user_id;
    private String Username;
    private UUID tenant_id;
    private UUID book_instance_id;
    private String registration_code;
    private UUID book_id;
    private String title;
    private String author;
    private  String publisher;
    private String isbn;
    private LocalDateTime due_date;
    private LocalDateTime returned_date;
    private boolean returned;

    public BorrowBookResponse(UUID borrow_Book_id, UUID user_id, String username, UUID tenant_id, UUID book_instance_id, String registration_code, UUID book_id, String title, String author, String publisher, String isbn, LocalDateTime due_date, LocalDateTime returned_date, boolean returned) {
        Borrow_Book_id = borrow_Book_id;
        this.user_id = user_id;
        Username = username;
        this.tenant_id = tenant_id;
        this.book_instance_id = book_instance_id;
        this.registration_code = registration_code;
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.due_date = due_date;
        this.returned_date = returned_date;
        this.returned = returned;
    }

    public UUID getBorrow_Book_id() {
        return Borrow_Book_id;
    }

    public void setBorrow_Book_id(UUID borrow_Book_id) {
        Borrow_Book_id = borrow_Book_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public UUID getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(UUID tenant_id) {
        this.tenant_id = tenant_id;
    }

    public UUID getBook_instance_id() {
        return book_instance_id;
    }

    public void setBook_instance_id(UUID book_instance_id) {
        this.book_instance_id = book_instance_id;
    }

    public String getRegistration_code() {
        return registration_code;
    }

    public void setRegistration_code(String registration_code) {
        this.registration_code = registration_code;
    }

    public UUID getBook_id() {
        return book_id;
    }

    public void setBook_id(UUID book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
