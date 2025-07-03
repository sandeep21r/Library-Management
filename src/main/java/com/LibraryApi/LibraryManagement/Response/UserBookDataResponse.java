package com.LibraryApi.LibraryManagement.Response;

import java.util.UUID;

public class UserBookDataResponse implements  ParentBookDataResponse{
    private UUID book_id;
    private String title;
    private String author;
    private String edition;
    private String language;
    private String isbn;
    private String publisher;
    private int quantity;

    public UserBookDataResponse(UUID book_id, String title, String author, String edition, String language, String isbn, String publisher, int quantity) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.language = language;
        this.isbn = isbn;
        this.publisher = publisher;
        this.quantity = quantity;
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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
