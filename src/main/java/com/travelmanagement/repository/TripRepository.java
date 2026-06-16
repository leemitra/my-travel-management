package com.travelmanagement.repository;

import com.travelmanagement.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByTravelerName(String travelerName);
    List<Trip> findByStatus(String status);
}
