package com.hust.itss.repositories.user;

import com.hust.itss.models.user.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    @Query("{ $or : [ { role : \"DRIVER\"}, { role : \"ASSISTANT\"} ] }")
    public Page<Employee> findAllEmployees(Pageable pageable);

    @Query("{ role : \"DRIVER\"}")
    public Page<Employee> findAllDrivers(Pageable pageable);

    @Query("{ role : \"ASSISTANT\"}")
    public Page<Employee> findAllAssistants(Pageable pageable);

    public Employee findEmployeeById(String id);

    @Query("{ '$or' : [ { phone : ?0 }, { email : ?1 }, { citizen_id : ?2 } ] }")
    public Employee findExistingEmployee(String phone, String email, String citizenId);

    @Query("{ name: { '$regex': ?0, '$options': 'i'}}")
    public Page<Employee> findEmployeesByName(String name, Pageable pageable);

    @Query("{ phone : { '$regex' : ?0, '$options' : 'i'}}")
    public Page<Employee> findEmployeesByPhoneNumber(String phoneNumber, Pageable pageable);

    @Query("{ name: { '$regex': ?0, '$options': 'i'}, phone : { '$regex' : ?1, '$options' : 'i'}}")
    public Page<Employee> findEmployeesByNameAndPhoneNumber(String name, String phoneNumber, Pageable pageable);


}
