package com.LibraryApi.LibraryManagement.Request;

import org.antlr.v4.runtime.misc.NotNull;

public class BookDataRequest {

    private String title;
    private String author;
    private String edition;
    private String language;
    private String isbn;
    private String publisher;
    private Integer quantity;
    public BookDataRequest() {
    }

    public BookDataRequest(String title, String author, String edition, String language, String isbn, String publisher, Integer quantity) {
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.language = language;
        this.isbn = isbn;

        this.publisher = publisher;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "BookDataRequest{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", edition='" + edition + '\'' +
                ", language='" + language + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
