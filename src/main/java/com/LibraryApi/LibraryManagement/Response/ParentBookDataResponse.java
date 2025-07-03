package com.LibraryApi.LibraryManagement.Response;

import java.util.List;
import java.util.UUID;

public interface ParentBookDataResponse {


    public UUID getBook_id();

    public String getTitle();


    public String getAuthor();
    public String getEdition();
    public String getLanguage();
    public String getIsbn();
    public int getQuantity();
    public String getPublisher();

}
