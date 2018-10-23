package com.hust.itss.models.schedules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "TransportSchedule")
@AllArgsConstructor
public class TransportSchedule extends Schedule {

    @Field("price")
    private Integer price;

    @Field("transporters")
    private List<String> transporters;
    
}
