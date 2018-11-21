package com.hust.itss.controllers.schedule;

import com.hust.itss.models.schedule.TransportSchedule;
import org.springframework.stereotype.Component;

@Component
public interface TransportScheduleAsyncTasks {
    public void insertTransportSchedule(TransportSchedule schedule);
    public void updateTransportSchedule(TransportSchedule target, TransportSchedule schedule);
    public void deleteTransporter(String id);
}
