package com.hust.itss.models.transporter;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "SeatAvailability")
public class SeatAvailability {
    public SeatAvailability(){}

    @Id
    private String id;

    @Field("route_ref")
    private String routeRef;

    @Field("schedule_ref")
    private String scheduleRef;

    @Field("date")
    private Date date;

    @Field("transporter_ref")
    private List<String> transporterRef;

    @Field("max")
    private Integer max;

    @Field("available_seats")
    private Integer availableSeats;

    public SeatAvailability(String id, String routeRef, String scheduleRef, Date date, List<String> transporterRef, Integer max, Integer availableSeats) {
        this.id = id;
        this.routeRef = routeRef;
        this.scheduleRef = scheduleRef;
        this.date = date;
        this.transporterRef = transporterRef;
        this.max = max;
        this.availableSeats = availableSeats;
    }
}
