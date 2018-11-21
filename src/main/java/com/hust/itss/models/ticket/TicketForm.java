package com.hust.itss.models.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@AllArgsConstructor
public class TicketForm {

    @Field("client")
    private String client;

    @Field("client_name")
    private String clientName;

    @Field("phone_number")
    private String phoneNumber;

    @Field("route")
    private String route;

    @Field("schedule")
    private String schedule;

    @Field("transporter")
    private String transporter;

    @Field("reservation_date")
    private Date reservationDate;

    @Field("specific_departure")
    private String specificDeparture;

    @Field("specific_destination")
    private String specificDestination;

    public TicketForm() {
    }
}
