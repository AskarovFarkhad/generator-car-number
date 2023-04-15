package com.example.numbergenerator.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "car_numbers")
@EqualsAndHashCode(exclude = {"id", "region", "country"})
public class CarNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_numbers_sequence")
    @SequenceGenerator(name = "car_numbers_sequence", allocationSize = 1)
    private Long id;

    @Builder.Default
    private UUID externalId = UUID.randomUUID();

    private String series;

    private String registrationNumber;

    @Builder.Default
    private String region = "116";

    @Builder.Default
    private String country = "RUS";

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public String toString() {
        return series.charAt(0) + registrationNumber + series.charAt(1) + series.charAt(2) + " " + region + " " + country;
    }
}