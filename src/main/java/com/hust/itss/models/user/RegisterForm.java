package com.hust.itss.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterForm {

    private String username;
    private String password;
    private String email;

    @JsonProperty(value = "full_name")
    private String fullName;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    private String address;

    public RegisterForm() {
    }
}
