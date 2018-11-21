package com.hust.itss.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Field;

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
}
