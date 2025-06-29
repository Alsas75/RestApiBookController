package javalessons.travelbox.service;

import javalessons.travelbox.model.Trip;
import javalessons.travelbox.model.TripRequest;
import javalessons.travelbox.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class TripService {

    private TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Trip saveTrip(TripRequest tripRequest) {
        Trip trip = new Trip();
        trip.setCities(tripRequest.getCities());
        trip.setEmail(tripRequest.getEmail());
        trip.setTitle(tripRequest.getTitle());
        trip.setOwnerName(tripRequest.getName());
        trip.setStartDate(tripRequest.getStartDate());
        trip.setEndDate(tripRequest.getEndDate());
        trip.setCreatedAt(LocalDateTime.now());
        trip.setData(tripRequest.toString().getBytes(StandardCharsets.UTF_8));
        return tripRepository.save(trip);
    }


}
