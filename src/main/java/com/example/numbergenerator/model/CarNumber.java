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

    @Builder.Default
    private UUID id = UUID.randomUUID();

    private String series;

    private String registrationNumber;

    @Builder.Default
    private String region = "116";

    @Builder.Default
    private String country = "RUS";

    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return series.charAt(0) + registrationNumber + series.charAt(1) + series.charAt(2) + " " + region + " " + country;
    }
}