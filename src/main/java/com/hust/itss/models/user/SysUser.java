package com.hust.itss.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Document(collection = "SystemUser")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUser {
    @Id
    private String id;

    @Field("username")
    @Length(max = 100)
    @NonNull
    private String username;

    @Field("password")
    @JsonIgnore
    private String password;

    @Field("email")
    @Length(max = 500)
    @NotBlank
    @Email
    private String email;

    @Field("role")
    private String role;

    @Field("active")
    private boolean active;

    @Field("first_name")
    @Length(max = 100)
    @JsonIgnore
    private String firstName;

    @Field("last_name")
    @Length(max = 100)
    @JsonIgnore
    private String lastName;

    @Field("full_name")
    @Length(max = 100)
    @JsonProperty("full_name")
    private String fullName;

    @URL
    @Field("link")
    @Length(max = 500)
    @JsonIgnore
    private String link;

    @Field("picture")
    @Length(max = 500)
    private String picture;

    @Length(min = 1, max = 255)
    @Field("authentication_service_id")
    @JsonIgnore
    private String authenticationServiceId;

    @Field("authentication_provider")
    @JsonIgnore
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

    public SysUser(@Length(max = 100) String username, @Length(max = 500) String email, String password, String role, @Length(max = 100) String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
    }
}
