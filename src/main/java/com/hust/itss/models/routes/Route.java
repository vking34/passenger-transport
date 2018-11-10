package com.hust.itss.models.routes;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Document(collection = "Route")
public class Route {
    @Id
    @NotNull
    private String id;

    @Field("departure")
    @NotNull
    private String departure;

    @Field("destination")
    @NotNull
    private String destination;

    @Field("stations")
    private List<String> stations;

    @Field("schedules")
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
