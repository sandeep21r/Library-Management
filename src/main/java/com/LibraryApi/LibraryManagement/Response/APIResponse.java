package com.LibraryApi.LibraryManagement.Response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"success", "message", "data"})
public class APIResponse <T> {

    private boolean Success;
    private String Message;
    private T data;

    public APIResponse(boolean Success, String Message, T data) {
        this.Success = true;
        this.Message = Message;
        this.data = data;
    }


    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setData(T data) {
        this.data = data;
    }


}