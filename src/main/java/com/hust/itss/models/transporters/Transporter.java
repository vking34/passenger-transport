package com.hust.itss.models.transporters;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "Transporter")
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

    @Field("schedule")
    private String schedule;

    @Field("route")
    private String route;

    public Transporter() {
    }

    public Transporter(String id, String licensePlate, Integer seaters, String model, String branch, Integer status, String schedule, String route) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.seaters = seaters;
        this.model = model;
        this.branch = branch;
        this.status = status;
        this.schedule = schedule;
        this.route = route;
    }
}
