package com.hust.itss.models.tickets;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "Ticket")
public class Ticket {
    @Id
    private String id;

    @Field("client")
    private String client;

    @Field("route")
    private String route;

    @Field("schedule")
    private String schedule;

    @Field("transporter")
    private String transporter;

    @Field("destination")
    private String destination;

    @Field("price")
    private Integer price;

    @Field("booking_date")
    private Date bookingDate;

    @Field("date")
    private Date date;
}
