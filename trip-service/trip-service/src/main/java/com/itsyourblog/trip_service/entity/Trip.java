package com.itsyourblog.trip_service.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "trips")
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String placeName;

    @Column(nullable = false)
    private LocalDate dateVisited;

    @Column(nullable = false)
    private BigDecimal budget;

    @Lob
    private String blog;

    @OneToMany(
            mappedBy = "trip",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<TripImage> images = new ArrayList<>();
}
