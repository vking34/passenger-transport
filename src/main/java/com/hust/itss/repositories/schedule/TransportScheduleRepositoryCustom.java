package com.hust.itss.repositories.schedule;

import com.hust.itss.models.schedules.TransportSchedule;

public interface TransportScheduleRepositoryCustom {
    public TransportSchedule findDetailOne(String id);
}
