package com.itsyourblog.trip_service.controller;

import com.itsyourblog.trip_service.dto.request.CreateTripRequest;
import com.itsyourblog.trip_service.dto.response.TripResponse;
import com.itsyourblog.trip_service.service.TripService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService)
    {
        this.tripService = tripService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public TripResponse createTrip(@ModelAttribute CreateTripRequest request) throws IOException {
        return tripService.createTrip(request);
    }

    @GetMapping
    public List<TripResponse> getAllTrip()
    {
        return tripService.getAllTrips();
    }

    @GetMapping("/{tripId}")
    public TripResponse getTripById(@PathVariable Long tripId)
    {
        return tripService.getTripById(tripId);
    }

    @PatchMapping("/{tripId}")
    public TripResponse updateTrip(@PathVariable Long tripId, @RequestBody CreateTripRequest request)
    {
        return tripService.updateTrip(tripId, request);
    }

    @DeleteMapping("/{tripId}")
    public void deleteTrip(@PathVariable Long tripId)
    {
        tripService.deleteTrip(tripId);
    }
}
