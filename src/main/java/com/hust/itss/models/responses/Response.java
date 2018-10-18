package com.hust.itss.models.responses;

import lombok.Data;

@Data
public class Response {
    private Boolean status;
    private Integer code;
    private String mess;

    public Response() {
    }

    public Response(Boolean status, Integer code, String mess) {
        this.status = status;
        this.code = code;
        this.mess = mess;
    }

    @Override
    public String toString(){
        return "{\"status\": " + status + ", \"code\": " + code + ", \"mess\": \"" + mess + "\"}";
    }

}
