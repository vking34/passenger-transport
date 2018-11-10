package com.hust.itss.repositories.transporter;

import com.hust.itss.models.transporters.Transporter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransporterRepository extends MongoRepository<Transporter, String> {

    public Transporter findTransporterById(String id);
    public Transporter findTransporterByLicensePlate(String licensePlate);

}
