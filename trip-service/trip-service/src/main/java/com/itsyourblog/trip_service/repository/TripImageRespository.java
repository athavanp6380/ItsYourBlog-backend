package com.itsyourblog.trip_service.repository;

import com.itsyourblog.trip_service.entity.TripImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripImageRespository extends JpaRepository<TripImage, Long> {

}
