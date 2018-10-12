package com.hust.itss.models.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "Employee")
public class Employee extends User {

    @Field("citizen_id")
    private String citizenId;

    @Field("work_schedule")
    private String workSchedule;

    public Employee(){
        super();
    }

    public Employee(String citizenId, String workSchedule) {
        this.citizenId = citizenId;
        this.workSchedule = workSchedule;
    }

    public Employee(String name, String phone, String email, String address, String citizenId, String workSchedule) {
        super(name, phone, email, address);
        this.citizenId = citizenId;
        this.workSchedule = workSchedule;
    }
}
