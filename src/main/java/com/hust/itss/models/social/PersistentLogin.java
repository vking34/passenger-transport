package com.hust.itss.models.social;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Document(collection = "PersistentLogin")
public class PersistentLogin {
    @Id
    @Field("series")
    @Size(max = 64)
    private String series;

    @Field("username")
    @Size(max = 64)
    private String username;

    @Field("token")
    @Size(max = 64)
    private String token;

    @Field("last_used")
    private Date lastUsed;

    public PersistentLogin() {
    }

    public PersistentLogin(@Size(max = 64) String series, @Size(max = 64) String username, @Size(max = 64) String token, Date lastUsed) {
        this.series = series;
        this.username = username;
        this.token = token;
        this.lastUsed = lastUsed;
    }
}
