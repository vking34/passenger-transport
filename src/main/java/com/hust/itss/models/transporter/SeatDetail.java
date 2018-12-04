package com.hust.itss.models.transporter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SeatDetail extends SeatAvailability {

    @Field("transporter")
    private List<Transporter> transporter;
}
