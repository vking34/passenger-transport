package com.hust.itss.models.schedules;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "WorkSchedule")
public class WorkSchedule extends Schedule {


    @Field("transporter")
    private String transporter;

    public WorkSchedule() {
        super();
    }

    public WorkSchedule(String transporter) {
        this.transporter = transporter;
    }

    public WorkSchedule(String id, String startingTime, String ending_time, String transporter) {
        super(id, startingTime, ending_time);
        this.transporter = transporter;
    }
}
