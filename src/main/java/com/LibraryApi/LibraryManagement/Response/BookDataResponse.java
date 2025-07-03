package com.LibraryApi.LibraryManagement.Response;


import java.util.List;
import java.util.UUID;

public class BookDataResponse implements  ParentBookDataResponse {
    private UUID book_id;
    private String title;
    private String author;
    private String edition;
    private String language;
    private String isbn;
    private String publisher;
    private int quantity;
    private List<BookInstanceResponse> bookInstanceResponseList;
    public BookDataResponse() {
    }

    public BookDataResponse(UUID book_Id, String title, String author, String edition, String language, String publisher, String isbn, int quantity,List<BookInstanceResponse> bookInstanceResponseList ) {
        this.book_id = book_Id;
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.language = language;
        this.isbn = isbn;
        this.quantity = quantity;
        this.publisher = publisher;
        this.bookInstanceResponseList = bookInstanceResponseList;
    }

    public BookDataResponse(UUID book_id, String title, String author, String edition, String language, String isbn, String publisher, int quantity) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.language = language;
        this.isbn = isbn;
        this.publisher = publisher;
        this.quantity = quantity;
    }

    @Override
    public UUID getBook_id() {
        return book_id;
    }

    public void setBook_id(UUID Book_Id) {
        this.book_id = Book_Id;
    }
    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    @Override
    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }
    @Override
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    @Override
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    @Override
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<BookInstanceResponse> getBookInstanceResponseList() {
        return bookInstanceResponseList;
    }

    public void setBookInstanceResponseList(List<BookInstanceResponse> bookInstanceResponseList) {
        this.bookInstanceResponseList = bookInstanceResponseList;
    }

    @Override
    public String toString() {
        return "BookDataResponse{" +
                "book_Id=" + book_id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", edition='" + edition + '\'' +
                ", language='" + language + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", quantity=" + quantity +
                ", bookInstanceResponseList=" + bookInstanceResponseList +
                '}';
    }
}
