package com.hust.itss.models.schedules;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hust.itss.models.transporters.Transporter;
import com.hust.itss.utils.ListObjectIdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "TransportSchedule")
@AllArgsConstructor
public class TransportSchedule extends Schedule {

    @Field("price")
    @JsonProperty(value = "price")
    private Integer price;

    @Field("transporter_refs")
    @JsonProperty(value = "transporter_refs")
    @JsonSerialize(using = ListObjectIdSerializer.class)    // custom writing json property
    private List<ObjectId> transporterRefs;

    private List<Transporter> transporters;

    public TransportSchedule(){
        super();
    }

}
