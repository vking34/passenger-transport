package com.hust.itss.models.schedules;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
public class Schedule {
    @Id
    private String id;

    @Field("starting_time")
    @JsonProperty(value = "starting_time")
    @NonNull
    private String startingTime;

    @Field("ending_time")
    @JsonProperty(value = "ending_time")
    @NonNull
    private String endingTime;

    public Schedule() {}
}
