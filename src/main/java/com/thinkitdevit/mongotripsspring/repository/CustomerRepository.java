package com.thinkitdevit.mongotripsspring.repository;

import com.thinkitdevit.mongotripsspring.models.Customer;
import com.thinkitdevit.mongotripsspring.models.aggregates.CustomerAggregate;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, ObjectId>{


        @Aggregation(pipeline = {
                "{ $match: { _id: ?0 } }",
                "{ $lookup: { from: 'bookings', localField: '_id', foreignField: 'customerId', as: 'bookings' } }",
                "{ $unwind: '$bookings' }",
                "{ $lookup: { from: 'trips', localField: 'bookings.tripId', foreignField: '_id', as: 'bookings.tripDetails' } }",
                "{ $unwind: '$bookings.tripDetails' }",
                "{ $addFields: { 'bookings.startDate': '$bookings.tripDetails.startDate', 'bookings.endDate': '$bookings.tripDetails.endDate',  'bookings.price': '$bookings.tripDetails.price',  'bookings.destination': '$bookings.tripDetails.destination',  'bookings.description': '$bookings.tripDetails.description' } }",
                "{ $group: { _id: '$_id', customer: { $first: { _id: '$_id', firstName: '$firstName', lastName: '$lastName', email: '$email', phoneNumber: '$phoneNumber', address: '$address' } }, bookings: { $push: '$bookings' } } }",
                "{ $project: { customer: 1, bookings: 1 } }"
        })
        Optional<CustomerAggregate> getCustomerAggregate(@Param("id") ObjectId id);


}
