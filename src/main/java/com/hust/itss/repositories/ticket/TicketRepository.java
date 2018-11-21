package com.hust.itss.repositories.ticket;

import com.hust.itss.models.ticket.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
    public Ticket findTicketById(String id);
}
