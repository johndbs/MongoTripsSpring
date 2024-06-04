package com.thinkitdevit.mongotripsspring;

import com.thinkitdevit.mongotripsspring.models.Booking;
import com.thinkitdevit.mongotripsspring.models.Customer;
import com.thinkitdevit.mongotripsspring.models.Trip;
import com.thinkitdevit.mongotripsspring.services.BookingService;
import com.thinkitdevit.mongotripsspring.services.CustomerService;
import com.thinkitdevit.mongotripsspring.services.TripService;
import com.thinkitdevit.mongotripsspring.services.aggregation.CustomerAggregateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class MongoTripsSpringApplication implements CommandLineRunner {

    private final CustomerService customerService;
    private final TripService tripService;
    private final BookingService bookingService;
    private final CustomerAggregateService customerAggregateService;

    public static void main(String[] args) {
        SpringApplication.run(MongoTripsSpringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Create a trip
        Trip tripParis = Trip.builder()
                .destination("Paris")
                .startDate(LocalDate.of(2024, 6, 1))
                .endDate(LocalDate.of(2024, 6, 10))
                .price(2041.99 )
                .availableSeats(1)
                .bookedSeats(0)
                .description("A trip to Paris")
                .build();

        tripParis = tripService.createTrip(tripParis);

        Trip tripLondon = Trip.builder()
                .destination("London")
                .startDate(LocalDate.of(2024, 7, 1))
                .endDate(LocalDate.of(2024, 7, 10))
                .price(1900.99 )
                .availableSeats(60)
                .bookedSeats(0)
                .description("A trip to London")
                .build();

        tripLondon = tripService.createTrip(tripLondon);

        // Read a trip by ID
        Optional<Trip> tripById = tripService.getTripById(tripParis.getId());
        tripById.ifPresentOrElse(
                tri -> System.out.println("Trip found: "+ tri.getDescription()),
                () -> System.out.println("Trip not found"));


        // Update the trip
        tripParis.setDescription("A beautiful trip to Paris");
        tripService.update(tripParis);

        // Read
        tripById = tripService.getTripById(tripParis.getId());
        tripById.ifPresentOrElse(
                tri -> System.out.println("Trip found: "+ tri.getDescription()),
                () -> System.out.println("Trip not found"));

        // Delete the trip
        tripService.delete( tripLondon.getId());

        // Get all trips
        tripService.getAllTrips().forEach(System.out::println);



        // Create a customer

        Customer customerAlice = Customer.builder()
                .firstName("Alice")
                .lastName("Smith")
                .email("alice.smith@test.com")
                .phoneNumber("0123456789")
                .address(Customer.Address.builder()
                        .street("1 street")
                        .city("city")
                        .zipCode("10000")
                        .country("FR")
                        .build())
                .build();

        customerAlice = customerService.createCustomer(customerAlice);

        Optional<Customer> customerFound = customerService.getCustomerById( customerAlice.getId());

        customerFound.ifPresentOrElse(
                cust -> System.out.println("Customer found: "+ cust.getFirstName()),
                () -> System.out.println("Customer not found"));

        // Create an other customer
        Customer customerBob = Customer.builder()
                .firstName("Bob")
                .lastName("Iron")
                .email("bob.iron@test.com")
                .phoneNumber("0123456789")
                .build();

        customerBob = customerService.createCustomer( customerBob);

        // Get customer by ID
        customerFound = customerService.getCustomerById( customerBob.getId());
        customerFound.ifPresentOrElse(
                cust -> System.out.println("Customer found: "+ cust.getFirstName()),
                () -> System.out.println("Customer not found"));

        // Delete a customer
        customerService.delete(customerBob.getId());


        // Create an other customer
        Customer customerCarl = Customer.builder()
                .firstName("Carl")
                .lastName("Iron")
                .email("carl.iron@test.com")
                .phoneNumber("0123456789")
                .build();

        customerCarl = customerService.createCustomer( customerCarl);

        // Get all customer
        customerService.getAllCustomers().forEach(System.out::println);



        // Create a first booking with transaction

        Booking createdBooking = null;


        try{
            createdBooking = bookingService.book( tripParis.getId(), customerAlice.getId());

            log.info("Booking created: "+createdBooking.getId());
        }catch (Exception e){
            log.error("Transaction fail : "+e);
        }


        // Get booking by ID
        Optional<Booking> bookingFound = bookingService.getBookingById( createdBooking.getId());
        bookingFound.ifPresentOrElse(
                book -> System.out.println("Booking found: "+ book.getId()),
                () -> System.out.println("Booking not found"));

        // Create a second booking with transaction

        createdBooking = null;

        try{
            createdBooking = bookingService.book( tripParis.getId(), customerCarl.getId());
        }catch (Exception e){
            log.error("Transaction fail : "+e);
        }


        // Get all booking
        bookingService.getAllBookings().forEach(System.out::println);


        // Delete a booking
        //bookingService.delete(bookingAliceToParis.getId());


        customerAggregateService.getCustomerAggregate( customerAlice.getId())
                .ifPresentOrElse(System.out::println,
                        () -> System.out.println("Booking aggregate not found"));

        customerAggregateService.getCustomerAggregateSimple( customerAlice.getId())
                .ifPresentOrElse(System.out::println,
                        () -> System.out.println("Booking aggregate not found"));


    }

}
