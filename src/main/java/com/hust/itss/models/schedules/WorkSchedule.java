package com.hust.itss.models.schedules;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hust.itss.models.transporters.Transporter;
import com.hust.itss.utils.ObjectIdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "WorkSchedule")
@AllArgsConstructor
public class WorkSchedule extends Schedule {

    @Field("transporter_ref")
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId transporterRef;

    private List<Transporter> transporter;
}
