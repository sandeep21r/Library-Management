package com.LibraryApi.LibraryManagement.Request;

import java.util.UUID;

public class BorrowRequest {
    private UUID bookId;

    public BorrowRequest(UUID bookId) {
        this.bookId = bookId;
    }

    public BorrowRequest() {
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }
}
