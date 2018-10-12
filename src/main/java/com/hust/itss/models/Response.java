package com.hust.itss.models;

import lombok.Data;

@Data
public class Response {
    private Boolean status;
    private Integer errorCode;
    private String errorMess;

    public Response() {
    }

    public Response(Boolean status, Integer errorCode, String errorMess) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMess = errorMess;
    }
}
