package com.hust.itss.models.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Schedule {
    @Id
    private String id;

    @Field("starting_time")
    @JsonProperty(value = "starting_time")
    @NotBlank
    private String startingTime;

    @Field("ending_time")
    @JsonProperty(value = "ending_time")
    @NotBlank
    private String endingTime;

    public Schedule() {}
}
