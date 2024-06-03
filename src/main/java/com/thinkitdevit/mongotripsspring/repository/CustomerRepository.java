package com.thinkitdevit.mongotripsspring.repository;

import com.thinkitdevit.mongotripsspring.models.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, ObjectId>{
}
