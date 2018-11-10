package com.hust.itss.models.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
class User extends SysUser {

    @Field("phone_number")
    private String phoneNumber;

    @Field("address")
    private String address;

    public User(){
        super();
    }

    public User(@Length(max = 100) String username, @Length(max = 500) String email, String password, String role, @Length(max = 100) String fullName, String phoneNumber, String address) {
        super(username, email, password, role, fullName);
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    //
//    public User(String id, @Length(max = 100) String username, @Length(max = 500) String email, String password, String role, boolean active, String infoRef, @Length(max = 100) String firstName, @Length(max = 100) String lastName, @Length(max = 100) String fullName, @URL @Length(max = 500) String link, @Length(max = 500) String picture, @Length(min = 1, max = 255) String authenticationServiceId, AuthenticationProvider authProvider, LocalDateTime firstLogon, String phoneNumber, String address) {
//        super(id, username, email, password, role, active, infoRef, firstName, lastName, fullName, link, picture, authenticationServiceId, authProvider, firstLogon);
//        this.phoneNumber = phoneNumber;
//        this.address = address;
//    }
//
//    public User(String phoneNumber, String address) {
//        this.phoneNumber = phoneNumber;
//        this.address = address;
//    }
//
//    public User(String username, String email, String password, String role, boolean active, String firstName, String lastName, String fullName, @URL String link, @Length(max = 500) String picture, @Length(min = 1, max = 255) String authenticationServiceId, AuthenticationProvider authProvider, LocalDateTime firstLogon, String phoneNumber, String address) {
//        super(username, email, password, role, active, firstName, lastName, fullName, link, picture, authenticationServiceId, authProvider, firstLogon);
//        this.phoneNumber = phoneNumber;
//        this.address = address;
//    }
}
