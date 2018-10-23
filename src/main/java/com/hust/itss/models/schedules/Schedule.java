package com.hust.itss.models.schedules;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Schedule {
    @Id
    private String id;

    @Field("starting_time")
    private String startingTime;

    @Field("ending_time")
    private String ending_time;

    public Schedule() {
    }

    public Schedule(String id, String startingTime, String ending_time) {
        this.id = id;
        this.startingTime = startingTime;
        this.ending_time = ending_time;
    }
}
