package com.hust.itss.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hust.itss.models.user.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    @JsonProperty(value = "status")
    private Boolean status;

    @JsonProperty(value = "access_token")
    private String token;

    @JsonProperty(value = "user")
    private SysUser sysUser;

    public TokenResponse(Boolean status){
        this.status = status;
    }
}
