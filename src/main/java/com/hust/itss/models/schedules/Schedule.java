package com.hust.itss.models.schedules;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Schedule {
    @Id
    private String id;

    @Field("starting_time")
    @JsonProperty(value = "starting_time")
    private String startingTime;

    @Field("ending_time")
    @JsonProperty(value = "ending_time")
    private String endingTime;

    public Schedule() {
    }

    public Schedule(String id, String startingTime, String endingTime) {
        this.id = id;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }
}
