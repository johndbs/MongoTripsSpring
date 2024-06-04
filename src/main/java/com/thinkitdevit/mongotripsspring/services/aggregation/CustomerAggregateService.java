package com.thinkitdevit.mongotripsspring.services.aggregation;

import com.thinkitdevit.mongotripsspring.mapper.Mapper;
import com.thinkitdevit.mongotripsspring.models.Customer;
import com.thinkitdevit.mongotripsspring.models.aggregates.CustomerAggregate;
import com.thinkitdevit.mongotripsspring.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@RequiredArgsConstructor
public class CustomerAggregateService {

    private final MongoTemplate mongoTemplate;

    private final Mapper<Customer> customerMapper;
    private final Mapper<CustomerAggregate.BookingAggregate> bookingAggregateMapper;

    private final CustomerRepository customerRepository;


    public Optional<CustomerAggregate> getCustomerAggregateSimple(ObjectId id) {
        return customerRepository.getCustomerAggregate(id);
    }

    public Optional<CustomerAggregate> getCustomerAggregate(ObjectId id) {
        AggregationOperation match = match(Criteria.where("_id").is(id));
        AggregationOperation lookupBookings = lookup("bookings", "_id", "customerId", "bookings");
        AggregationOperation unwindBookings = unwind("bookings");
        AggregationOperation lookupTrips = lookup("trips", "bookings.tripId", "_id", "bookings.tripDetails");
        AggregationOperation unwindBookingsTripDetails = unwind("bookings.tripDetails");
        AggregationOperation groupTripDetails = group("_id")
                .first("firstName").as("firstName")
                .first("lastName").as("lastName")
                .first("email").as("email")
                .first("phoneNumber").as("phoneNumber")
                .first("address").as("address")
                .push("bookings").as("bookings");
        AggregationOperation projectTripDetails = project("firstName", "lastName", "email", "phoneNumber", "address", "bookings");

        Aggregation aggregation = newAggregation(
                match,
                lookupBookings,
                unwindBookings,
                lookupTrips,
                unwindBookingsTripDetails,
                groupTripDetails,
                projectTripDetails
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "customers", Document.class);

        return Optional.ofNullable(results.getUniqueMappedResult())
                .map(this::documentToCustomerAggregate);
    }

    private CustomerAggregate documentToCustomerAggregate(Document document){


        List<CustomerAggregate.BookingAggregate> bookingAggregates = Optional.of(document.get("bookings"))
                .map((doc) -> (List<Document>)doc)
                .orElse(List.of())
                .stream()
                .map(bookingAggregateMapper::documentToModel)
                .collect(Collectors.toList());

        CustomerAggregate customerAggregate = CustomerAggregate.builder()
                .customer(customerMapper.documentToModel(document))
                .bookings(bookingAggregates)
                .build();

        return customerAggregate;
    }
}
