package com.hust.itss.repositories.user;

import com.hust.itss.models.user.Client;
import com.hust.itss.models.user.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {

    @Query("{ role : \"USER\" }")
    public Page<Client> findAllClients(Pageable pageable);

    public Client findClientById(String id);

    @Query("{ '$or' : [ { phone : ?0 }, { email : ?1 } ] }")
    public Client findExistingClient(String phone, String email);

    @Query("{ '$or' : [ {username: ?0 }, { phone : ?1 }, { email : ?2 } ] }")
    public Client findExistingUser(String username, String phone, String email);

//    @Query("{ '$or' : [ { name: { '$regex': ?0, '$options': 'i'} }, { phone : { '$regex' : ?1, '$options' : 'i'} } ] }")
    @Query("{ name: { '$regex': ?0, '$options': 'i'}, phone : { '$regex' : ?1, '$options' : 'i'} }")
    public Page<Client> filterClients(String name, String phone, Pageable pageable);
}
