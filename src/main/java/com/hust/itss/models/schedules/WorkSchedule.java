package com.hust.itss.models.schedules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "WorkSchedule")
@AllArgsConstructor
public class WorkSchedule extends Schedule {

    @Field("transporter")
    private String transporter;
}
