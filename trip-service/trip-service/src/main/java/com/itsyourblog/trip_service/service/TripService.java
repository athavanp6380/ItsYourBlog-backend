package com.itsyourblog.trip_service.service;

import com.itsyourblog.trip_service.dto.request.CreateTripRequest;
import com.itsyourblog.trip_service.dto.response.TripResponse;
import com.itsyourblog.trip_service.entity.Trip;

import java.io.IOException;
import java.util.List;

public interface TripService {
    TripResponse createTrip(CreateTripRequest request) throws IOException;
    List<TripResponse> getAllTrips();
    TripResponse getTripById(Long tripId);
    TripResponse updateTrip(Long tripId, CreateTripRequest reques);
    void deleteTrip(Long tripId);
}
