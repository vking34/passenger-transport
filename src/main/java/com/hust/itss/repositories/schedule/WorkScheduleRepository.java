package com.hust.itss.repositories.schedule;

import com.hust.itss.models.schedules.WorkSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkScheduleRepository extends MongoRepository<WorkSchedule, String>, WorkScheduleRepositoryCustom {
    public WorkSchedule findWorkScheduleById(String id);
}
