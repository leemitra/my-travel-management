package com.travelmanagement.controller;

import com.travelmanagement.model.Trip;
import com.travelmanagement.repository.TripRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class TripController {

    private final TripRepository tripRepository;

    /**
     * Get all trips
     */
    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripRepository.findAll();
        return ResponseEntity.ok(trips);
    }

    /**
     * Get trip by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        Optional<Trip> trip = tripRepository.findById(id);
        return trip.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Get trips by traveler name
     */
    @GetMapping("/traveler/{travelerName}")
    public ResponseEntity<List<Trip>> getTripsByTravelerName(@PathVariable String travelerName) {
        List<Trip> trips = tripRepository.findByTravelerName(travelerName);
        return ResponseEntity.ok(trips);
    }

    /**
     * Get trips by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Trip>> getTripsByStatus(@PathVariable String status) {
        List<Trip> trips = tripRepository.findByStatus(status);
        return ResponseEntity.ok(trips);
    }

    /**
     * Create new trip
     */
    @PostMapping
    public ResponseEntity<Trip> createTrip(@Valid @RequestBody Trip trip) {
        Trip savedTrip = tripRepository.save(trip);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTrip);
    }

    /**
     * Update trip
     */
    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @Valid @RequestBody Trip tripDetails) {
        Optional<Trip> tripOptional = tripRepository.findById(id);

        if (tripOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Trip trip = tripOptional.get();
        trip.setDestination(tripDetails.getDestination());
        trip.setDescription(tripDetails.getDescription());
        trip.setStartDate(tripDetails.getStartDate());
        trip.setEndDate(tripDetails.getEndDate());
        trip.setTravelerName(tripDetails.getTravelerName());
        trip.setBudget(tripDetails.getBudget());
        trip.setStatus(tripDetails.getStatus());

        Trip updatedTrip = tripRepository.save(trip);
        return ResponseEntity.ok(updatedTrip);
    }

    /**
     * Delete trip
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        if (!tripRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        tripRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health/status")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Travel Management API is running!");
    }

}
