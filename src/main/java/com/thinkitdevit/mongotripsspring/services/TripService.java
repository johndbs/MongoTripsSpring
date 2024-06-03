package com.thinkitdevit.mongotripsspring.services;

import com.thinkitdevit.mongotripsspring.mapper.TripMapper;
import com.thinkitdevit.mongotripsspring.models.Trip;
import com.thinkitdevit.mongotripsspring.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public Optional<Trip> getTripById(ObjectId id) {
        return tripRepository.findById(id);
    }

    public void update(Trip tripParis) {
        tripRepository.save(tripParis);
    }

    public void delete(ObjectId id) {
        tripRepository.deleteById(id);
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }
}
