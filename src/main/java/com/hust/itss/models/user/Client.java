package com.hust.itss.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Document(collection = "SystemUser")
public class Client extends User {

    @Field("point")
    private Integer point;

    public Client(@Length(max = 100) String username, @Length(max = 500) String email, String password, String role, @Length(max = 100) String fullName, String phoneNumber, String address, Integer point) {
        super(username, email, password, role, fullName, phoneNumber, address);
        this.point = point;
    }
}
