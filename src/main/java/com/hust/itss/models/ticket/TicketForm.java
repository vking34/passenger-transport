package com.hust.itss.models.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hust.itss.utils.deserializer.DateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@AllArgsConstructor
public class TicketForm {

    @Field("phone_number")
    private String phoneNumber;

    @Field("route")
    private String route;

    @Field("schedule")
    private String schedule;

    @Field("transporter")
    private String transporter;

    @Field("reservation_date")
    @JsonProperty("reservation_date")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date reservationDate;

    @Field("ticket_quantity")
    private Integer ticketQuantity;

    @Field("client_name")
    private String clientName;

    @Field("age")
    private Integer age;

    @Field("gender")
    private String gender;

    @Field("specific_departure")
    private String specificDeparture;

    @Field("specific_destination")
    private String specificDestination;

    @Field("client_id")
    private String clientId;

    public TicketForm() {
    }
}
