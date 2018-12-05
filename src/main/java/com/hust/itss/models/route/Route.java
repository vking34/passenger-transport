package com.hust.itss.models.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hust.itss.utils.serializer.ListObjectIdSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
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
    @JsonProperty(value = "schedules")
    @JsonSerialize(using = ListObjectIdSerializer.class)
    private List<ObjectId> schedules;

    public Route() { }

    public Route(@NotNull String id, @NotNull String departure, @NotNull String destination, List<String> stations, List<ObjectId> schedules) {
        this.id = id;
        this.departure = departure;
        this.destination = destination;
        this.stations = stations;
        this.schedules = schedules;
    }
}
