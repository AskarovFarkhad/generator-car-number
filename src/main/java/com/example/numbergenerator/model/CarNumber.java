package com.example.numbergenerator.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id", "region", "country"})
public class CarNumber {

    private UUID id;

    private String frontSeries;

    private String registrationNumber;

    private String backSeries;

    private String region;

    private String country;

    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return frontSeries + registrationNumber + backSeries + " " + region + " " + country;
    }
}