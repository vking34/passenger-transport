package com.hust.itss.models.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "Client")
public class Client extends User {

    @Field("point")
    private Integer point;

    public Client(){
        super();
    }

    public Client(Integer point) {
        this.point = point;
    }

    public Client(String name, String phone, String email, String address, Integer point) {
        super(name, phone, email, address);
        this.point = point;
    }
}
