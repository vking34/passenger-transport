package com.hust.itss.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
}
