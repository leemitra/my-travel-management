package com.travelmanagement.service;

import com.travelmanagement.model.Trip;
import com.travelmanagement.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripService {

    private final TripRepository tripRepository;

    /**
     * Get all trips
     */
    @Transactional(readOnly = true)
    public List<Trip> getAllTrips() {
        log.info("Fetching all trips");
        return tripRepository.findAll();
    }

    /**
     * Get trip by ID
     */
    @Transactional(readOnly = true)
    public Optional<Trip> getTripById(Long id) {
        log.info("Fetching trip with ID: {}", id);
        return tripRepository.findById(id);
    }

    /**
     * Create new trip
     */
    @Transactional
    public Trip createTrip(Trip trip) {
        log.info("Creating new trip for traveler: {}", trip.getTravelerName());
        return tripRepository.save(trip);
    }

    /**
     * Update trip
     */
    @Transactional
    public Optional<Trip> updateTrip(Long id, Trip tripDetails) {
        log.info("Updating trip with ID: {}", id);
        return tripRepository.findById(id).map(trip -> {
            trip.setDestination(tripDetails.getDestination());
            trip.setDescription(tripDetails.getDescription());
            trip.setStartDate(tripDetails.getStartDate());
            trip.setEndDate(tripDetails.getEndDate());
            trip.setTravelerName(tripDetails.getTravelerName());
            trip.setBudget(tripDetails.getBudget());
            trip.setStatus(tripDetails.getStatus());
            return tripRepository.save(trip);
        });
    }

    /**
     * Delete trip
     */
    @Transactional
    public boolean deleteTrip(Long id) {
        log.info("Deleting trip with ID: {}", id);
        if (tripRepository.existsById(id)) {
            tripRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Get trips by traveler name
     */
    @Transactional(readOnly = true)
    public List<Trip> getTripsByTravelerName(String travelerName) {
        log.info("Fetching trips for traveler: {}", travelerName);
        return tripRepository.findByTravelerName(travelerName);
    }

    /**
     * Get trips by status
     */
    @Transactional(readOnly = true)
    public List<Trip> getTripsByStatus(String status) {
        log.info("Fetching trips with status: {}", status);
        return tripRepository.findByStatus(status);
    }

}
