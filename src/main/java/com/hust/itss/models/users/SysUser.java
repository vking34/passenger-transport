package com.hust.itss.models.users;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "SystemUser")
public class SysUser {
    @Id
    private String id;

    @Field("username")
    private String username;

    @Field("email")
    private String email;

    @Field("password")
    private String password;

    @Field("role")
    private String role;

    @Field("active")
    private boolean active;

    @Field("info_ref")
    private String infoRef;

    public SysUser() {
    }

    public SysUser(String id, String username, String email, String password, String role, boolean active, String infoRef) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;
        this.infoRef = infoRef;
    }

    public SysUser(String username, String email, String password, String role, boolean active) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;
    }
}
