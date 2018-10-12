package com.hust.itss.models.schedules;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "TransportSchedule")
public class TransportSchedule extends Schedule {

    @Field("price")
    private Integer price;

    @Field("transporters")
    private List<String> transporters;

    public TransportSchedule(Integer price, List<String> transporters) {
        this.price = price;
        this.transporters = transporters;
    }

    public TransportSchedule(String id, String startingTime, String ending_time, Integer price, List<String> transporters) {
        super(id, startingTime, ending_time);
        this.price = price;
        this.transporters = transporters;
    }
}
