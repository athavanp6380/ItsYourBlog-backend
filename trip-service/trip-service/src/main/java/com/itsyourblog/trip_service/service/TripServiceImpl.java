package com.itsyourblog.trip_service.service;

import com.itsyourblog.trip_service.dto.request.CreateTripRequest;
import com.itsyourblog.trip_service.dto.response.TripResponse;
import com.itsyourblog.trip_service.entity.Trip;
import com.itsyourblog.trip_service.entity.TripImage;
import com.itsyourblog.trip_service.exception.TripNotFoundException;
import com.itsyourblog.trip_service.repository.TripImageRespository;
import com.itsyourblog.trip_service.repository.TripRepository;
import com.itsyourblog.trip_service.security.AuthenticatedUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripServiceImpl implements TripService{

    private final TripRepository tripRepository;
    private final TripImageRespository tripImageRespository;
    private final FileStorageService fileStorageService;

    public TripServiceImpl(TripRepository tripRepository, TripImageRespository tripImageRespository, FileStorageService fileStorageService)
    {
        this.tripRepository = tripRepository;
        this.tripImageRespository = tripImageRespository;
        this.fileStorageService = fileStorageService;
    }
    @Override
    public TripResponse createTrip(CreateTripRequest request) throws IOException {

        AuthenticatedUser authenticatedUser =
                (AuthenticatedUser) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();


        Trip trip = new Trip();
        trip.setPlaceName(request.getPlaceName());
        trip.setDateVisited(request.getDateVisited());
        trip.setBudget(request.getBudget());
        trip.setBlog(request.getBlog());
        trip.setUserId(authenticatedUser.getUserId());

        Trip savedTrip = tripRepository.save(trip);

        if (request.getImages() != null) {

            for (MultipartFile file : request.getImages()) {

                if (file.isEmpty()) {
                    continue;
                }

                String fileName = fileStorageService.storeFile(file);

                TripImage image = new TripImage();
                image.setImageUrl(fileName);
                image.setTrip(savedTrip);
                savedTrip.getImages().add(image);
                tripImageRespository.save(image);
            }
        }
        TripResponse response = new TripResponse();

        response.setTripId(savedTrip.getTripId());
        response.setPlaceName(savedTrip.getPlaceName());
        response.setDateVisited(savedTrip.getDateVisited());
        response.setBudget(savedTrip.getBudget());
        response.setBlog(savedTrip.getBlog());

        List<String> imageUrls = savedTrip.getImages()
                .stream()
                .map(TripImage::getImageUrl)
                .toList();

        response.setImages(imageUrls);

        return response;
    }

    @Override
    public List<TripResponse> getAllTrips() {

        AuthenticatedUser authenticatedUser =
                (AuthenticatedUser) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        Long userId = authenticatedUser.getUserId();
        List<Trip> trips = tripRepository.findByUserId(userId);

        List<TripResponse> responses = new ArrayList<>();

        for(Trip trip : trips)
        {
            TripResponse response = new TripResponse();

            response.setTripId(trip.getTripId());
            response.setPlaceName(trip.getPlaceName());
            response.setDateVisited(trip.getDateVisited());
            response.setBudget(trip.getBudget());
            response.setBlog(trip.getBlog());

            List<String> imageUrls = trip.getImages()
                    .stream()
                    .map(TripImage::getImageUrl)
                    .toList();

            response.setImages(imageUrls);
            
            responses.add(response);
        }
        return responses;
    }

    @Override
    public TripResponse getTripById(Long tripId) {
        AuthenticatedUser authenticatedUser =
                (AuthenticatedUser) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        Long currentUserId = authenticatedUser.getUserId();

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() ->
                        new TripNotFoundException("Trip not found with Id: " + tripId));

        if (!trip.getUserId().equals(currentUserId)) {
            throw new RuntimeException("Access Denied");
        }

        TripResponse response = new TripResponse();

        response.setTripId(trip.getTripId());
        response.setPlaceName(trip.getPlaceName());
        response.setDateVisited(trip.getDateVisited());
        response.setBudget(trip.getBudget());
        response.setBlog(trip.getBlog());
        List<String> imageUrls = trip.getImages()
                .stream()
                .map(TripImage::getImageUrl)
                .toList();

        response.setImages(imageUrls);
        return response;
    }

    @Override
    public TripResponse updateTrip(Long tripId, CreateTripRequest request) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()-> new TripNotFoundException("Trip not found with Id: "+tripId));
        AuthenticatedUser authenticatedUser =
                (AuthenticatedUser) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        Long currentUserId = authenticatedUser.getUserId();

        if (!trip.getUserId().equals(currentUserId)) {
            throw new RuntimeException("Access Denied");
        }

        trip.setPlaceName(request.getPlaceName());
        trip.setDateVisited(request.getDateVisited());
        trip.setBudget(request.getBudget());
        trip.setBlog(request.getBlog());

        Trip savedTrip = tripRepository.save(trip);
        TripResponse response = new TripResponse();

        response.setTripId(savedTrip.getTripId());
        response.setPlaceName(savedTrip.getPlaceName());
        response.setDateVisited(savedTrip.getDateVisited());
        response.setBudget(savedTrip.getBudget());
        response.setBlog(savedTrip.getBlog());

        return response;
    }

    @Override
    public void deleteTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() ->
                        new TripNotFoundException("Trip not found with id: " + tripId));
        AuthenticatedUser authenticatedUser =
                (AuthenticatedUser) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        Long currentUserId = authenticatedUser.getUserId();

        if (!trip.getUserId().equals(currentUserId)) {
            throw new RuntimeException("Access Denied");
        }
        tripRepository.delete(trip);
    }
}
