package com.hust.itss.models.transporter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hust.itss.utils.serializer.ListObjectIdSerializer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@Document(collection = "SeatAvailability")
public class SeatDetail {
    @Id
    private String id;

    @Field("transporter")
    private Transporter transporter;

    @Field("route_ref")
    private String routeRef;

    @Field("schedule_ref")
    private String scheduleRef;

    @Field("date")
    private Date date;

//    @Field("transporter_ref")
//    @JsonSerialize(using = ListObjectIdSerializer.class)
//    private List<ObjectId> transporterRef;

    @Field("max")
    private Integer max;

    @Field("available_seats")
    private Integer availableSeats;

    public SeatDetail() {
    }


}
