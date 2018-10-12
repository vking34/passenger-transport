package com.hust.itss.repositories;

import com.hust.itss.models.schedules.TransportSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportScheduleRepository extends MongoRepository<TransportSchedule, String> {
}
