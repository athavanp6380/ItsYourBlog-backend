package com.itsyourblog.trip_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTripRequest {
    private String placeName;
    private LocalDate dateVisited;
    private BigDecimal budget;
    private String blog;
    private MultipartFile[] images;
}
