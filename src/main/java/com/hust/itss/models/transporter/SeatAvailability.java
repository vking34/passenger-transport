package com.hust.itss.models.transporter;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "SeatAvailability")
@AllArgsConstructor
public class SeatAvailability {
    @Id
    private String id;

    @Field("route_ref")
    private String routeRef;

    @Field("schedule_ref")
    private String scheduleRef;

    @Field("transporter_ref")
    private String transporterRef;

    @Field("date")
    private Date date;

    @Field("max")
    private Integer max;

    @Field("available_seats")
    private Integer availableSeats;
}
