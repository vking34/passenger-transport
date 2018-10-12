package com.hust.itss.models.routes;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "Route")
public class Route {
    @Id
    private String id;

    @Field("departure")
    private String departure;

    @Field("destination")
    private String destination;

    @Field("stations")
    private List<String> stations;

    @Field("schedule")
    private List<String> schedules;

    public Route() { }

    public Route(String id, String departure, String destination, List<String> stations, List<String> schedules) {
        this.id = id;
        this.departure = departure;
        this.destination = destination;
        this.stations = stations;
        this.schedules = schedules;
    }
}
