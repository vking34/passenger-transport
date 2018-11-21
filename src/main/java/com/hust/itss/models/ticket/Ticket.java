package com.hust.itss.models.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "Ticket")
@AllArgsConstructor
public class Ticket extends TicketForm {
    @Id
    @JsonProperty(value = "id", required = false)
    private String id;

    @Field("price")
    @JsonProperty(value = "price", required = false)
    private Integer price;

    @Field("date_created")
    @JsonProperty(value = "date_created", required = false)
    private Date dateCreated;

    public Ticket(){
        super();
    }
}
