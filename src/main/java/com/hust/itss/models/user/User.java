package com.hust.itss.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
class User extends SysUser {

    @Field("phone_number")
    @Pattern(regexp="(^$|[0-9]{10})")
    @NotBlank
    private String phoneNumber;

    @Field("address")
    private String address;

    public User(){
        super();
    }

    public User(@Length(max = 100) @NotBlank @NonNull String username, @Length(max = 500) String email, String password,@NonNull String role, @Length(max = 100) @NonNull String fullName, @NonNull @Pattern(regexp="(^$|[0-9]{10})") @Length(max = 10)  String phoneNumber, String address) {
        super(username, email, password, role, fullName);
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
