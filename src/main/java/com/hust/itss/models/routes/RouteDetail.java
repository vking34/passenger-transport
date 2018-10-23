package com.hust.itss.models.routes;

import com.hust.itss.models.schedules.TransportSchedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class RouteDetail extends Route {

    @Field("transport_schedules")
    private List<TransportSchedule> transportSchedules;
}
