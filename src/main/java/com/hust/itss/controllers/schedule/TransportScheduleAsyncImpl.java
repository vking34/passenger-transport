package com.hust.itss.controllers.schedule;

import com.hust.itss.models.schedules.TransportSchedule;
import com.hust.itss.repositories.schedule.TransportScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TransportScheduleAsyncImpl implements TransportScheduleAsyncTasks {

    @Autowired
    private TransportScheduleRepository scheduleRepository;

    @Async
    @Override
    public void insertTransportSchedule(TransportSchedule schedule) {
        scheduleRepository.insert(schedule);
    }

    @Async
    @Override
    public void updateTransportSchedule(TransportSchedule target, TransportSchedule schedule) {
        if (!target.getPrice().equals(schedule.getPrice()))
            target.setPrice(schedule.getPrice());
        if (!target.getEndingTime().equals(schedule.getEndingTime()))
            target.setEndingTime(schedule.getEndingTime());
        if (!target.getStartingTime().equals(schedule.getStartingTime()))
            target.setStartingTime(schedule.getStartingTime());
        if (schedule.getTransporterRefs() != null)
            target.setTransporterRefs(schedule.getTransporterRefs());
        scheduleRepository.save(target);
    }

    @Async
    @Override
    public void deleteTransporter(String id) {
        scheduleRepository.deleteById(id);
    }
}
