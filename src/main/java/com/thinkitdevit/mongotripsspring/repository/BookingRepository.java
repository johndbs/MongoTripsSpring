package com.thinkitdevit.mongotripsspring.repository;

import com.thinkitdevit.mongotripsspring.models.Booking;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends MongoRepository<Booking, ObjectId>{
}
