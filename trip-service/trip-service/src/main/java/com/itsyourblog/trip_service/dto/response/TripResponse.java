package com.itsyourblog.trip_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripResponse {
    private Long tripId;
    private String placeName;
    private LocalDate dateVisited;
    private BigDecimal budget;
    private String blog;
    private List<String> images;
}
