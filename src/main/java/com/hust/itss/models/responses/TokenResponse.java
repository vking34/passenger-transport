package com.hust.itss.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    private boolean status;
    private String token;

    @Override
    public String toString(){
        return "{\"status\": " + status + ", \"token\": \"" + token + "\"}";
    }
}
