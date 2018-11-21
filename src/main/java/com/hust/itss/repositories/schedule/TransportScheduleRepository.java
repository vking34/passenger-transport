package com.hust.itss.repositories.schedule;

import com.hust.itss.models.schedule.TransportSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportScheduleRepository extends MongoRepository<TransportSchedule, String>, TransportScheduleRepositoryCustom {
    public TransportSchedule findTransportScheduleById(String id);

    @Query("{ price: ?0, starting_time: ?1, ending_time: ?2 }")
    public TransportSchedule findOneExists(Integer price, String startingTime, String endingTime);

}
