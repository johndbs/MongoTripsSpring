package com.thinkitdevit.mongotripsspring.repository;

import com.thinkitdevit.mongotripsspring.models.Trip;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends MongoRepository<Trip, ObjectId> {
}
