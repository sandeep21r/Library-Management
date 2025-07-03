package com.LibraryApi.LibraryManagement.Response;

import java.util.UUID;

public class BookInstanceResponse {
    private UUID book_instance_id;
    private boolean available;

    public BookInstanceResponse(UUID book_instance_id, boolean available) {
        this.book_instance_id = book_instance_id;
        this.available = available;
    }

    public UUID getBook_instance_id() {
        return book_instance_id;
    }

    public void setBook_instance_id(UUID book_instance_id) {
        this.book_instance_id = book_instance_id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
