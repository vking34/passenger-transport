package com.hust.itss.models.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@Document(collection = "SystemUser")
public class SysUser {
    @Id
    private String id;

    @Field("username")
    @Length(max = 100)
    private String username;

    @Field("email")
    @Length(max = 500)
    private String email;

    @Field("password")
    private String password;

    @Field("role")
    private String role;

    @Field("active")
    private boolean active;

    @Field("info_ref")
    private String infoRef;

    @Field("first_name")
    @Length(max = 100)
    private String firstName;

    @Field("last_name")
    @Length(max = 100)
    private String lastName;

    @Field("fullname")
    @Length(max = 100)
    private String fullName;

    @URL
    @Field("link")
    @Length(max = 500)
    private String link;

    @Field("picture")
    @Length(max = 500)
    private String picture;

    @Length(min = 1, max = 255)
    @Field("authentication_service_id")
    private String authenticationServiceId;

    @Field("authentication_provider")
    private AuthenticationProvider authProvider;

    @Field("first_logon")
    private LocalDateTime firstLogon;

    public enum AuthenticationProvider {
        GOOGLE(0),
        FACEBOOK(1);

        private final int provider;

        AuthenticationProvider(int provider) {
            this.provider = provider;
        }

        public int getAuthProvider() {
            return this.provider;
        }
    }

    public SysUser() {
    }

    public SysUser(String username, String email, String password, String role, boolean active, String firstName, String lastName, String fullName,@URL String link, @Length(max = 500) String picture, @Length(min = 1, max = 255) String authenticationServiceId, AuthenticationProvider authProvider, LocalDateTime firstLogon) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.link = link;
        this.picture = picture;
        this.authenticationServiceId = authenticationServiceId;
        this.authProvider = authProvider;
        this.firstLogon = firstLogon;
    }
}
