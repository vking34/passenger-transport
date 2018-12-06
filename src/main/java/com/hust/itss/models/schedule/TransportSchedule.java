package com.hust.itss.models.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hust.itss.models.transporter.Transporter;
import com.hust.itss.utils.serializer.ListObjectIdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
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
    @NonNull
    private Integer price;

    @Field("transporter_refs")
    @JsonProperty(value = "transporter_refs")
    @JsonSerialize(using = ListObjectIdSerializer.class)    // custom writing json property
    private List<ObjectId> transporterRefs;


    @Field("transporters")
    @JsonProperty("transporters")
    private List<Transporter> transporters;

    public TransportSchedule(){
        super();
    }

}
