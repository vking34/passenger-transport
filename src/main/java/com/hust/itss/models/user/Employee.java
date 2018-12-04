package com.hust.itss.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Document(collection = "SystemUser")
public class Employee extends User {
    public Employee(){
        super();
    }

    @Field("citizen_id")
    private String citizenId;

    @Field("work_schedule")
    private String workSchedule;
}
