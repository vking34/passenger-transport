package com.hust.itss.models.transporters;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hust.itss.models.routes.Route;
import com.hust.itss.models.schedules.TransportSchedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "Transporter")
@AllArgsConstructor
public class Transporter {
    @Id
    private String id;

    @Field("license_plate")
    private String licensePlate;

    @Field("seaters")
    private Integer seaters;

    @Field("model")
    private String model;

    @Field("branch")
    private String branch;

    @Field("status")
    private Integer status;

    @Field("schedule_ref")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // not in the response json
    private String scheduleRef;

    @Field("route_ref")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String routeRef;

//    @Transient
    private Route route;

//    @Transient
    private TransportSchedule schedule;

    public Transporter() {
    }
}
