package com.hust.itss.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Driver extends Employee {

    @Field("driving_license_class")
    private String drivingLicenseClass;
}
