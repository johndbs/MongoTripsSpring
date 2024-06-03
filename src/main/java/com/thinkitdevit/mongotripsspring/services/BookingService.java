package com.thinkitdevit.mongotripsspring.services;

import com.thinkitdevit.mongotripsspring.exception.SelloutException;
import com.thinkitdevit.mongotripsspring.mapper.BookingMapper;
import com.thinkitdevit.mongotripsspring.models.Booking;
import com.thinkitdevit.mongotripsspring.models.Trip;
import com.thinkitdevit.mongotripsspring.repository.BookingRepository;
import com.thinkitdevit.mongotripsspring.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TripRepository tripRepository;
    private final BookingMapper bookingMapper;


    @Transactional(rollbackFor = SelloutException.class)
    public Booking book(ObjectId tripId, ObjectId customerId) {
        log.info("Booking tripId:{} customerId:{}", tripId, customerId);

        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalArgumentException("Trip not found tripId: "+tripId));

        trip.setBookedSeats(trip.getBookedSeats() + 1);

        trip = tripRepository.save(trip);


        Booking booking = Booking.builder()
                .date(LocalDateTime.now())
                .status(Booking.Status.PENDING)
                .customerId(customerId)
                .tripId(tripId)
                .build();

        booking = bookingRepository.save(booking);

        if(trip.getBookedSeats() > trip.getAvailableSeats()){
            throw new SelloutException("All seats are sellout tripId:"+tripId);
        }


        return booking;
    }


    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBookingById(ObjectId id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
