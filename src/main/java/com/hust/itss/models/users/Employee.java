package com.hust.itss.models.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Document(collection = "SystemUser")
public class Employee extends User {

    @Field("citizen_id")
    private String citizenId;

    @Field("work_schedule")
    private String workSchedule;
}
