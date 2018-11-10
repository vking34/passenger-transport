package com.hust.itss.models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;

@Data
@AllArgsConstructor
public class UserRegisterForm {

    private String username;
    private String password;
    private String email;

    @JsonProperty(value = "full_name")
    private String fullName;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    private String address;

    public UserRegisterForm() {
    }
}
