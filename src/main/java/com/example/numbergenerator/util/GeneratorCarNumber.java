package com.example.numbergenerator.util;

import com.example.numbergenerator.model.CarNumber;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
public class GeneratorCarNumber {

    private static final String[] LETTERS = {"А", "Е", "Т", "О", "Р", "Н", "У", "К", "Х", "С", "В", "М"};

    private static final int MAX_LETTER_INDEX = LETTERS.length - 1;

    private static final int MAX_DIGIT = 999;

    private static final String REGION = "116";

    private static final String COUNTRY = "RUS";

    private static final Random random = new Random();

    private final Set<CarNumber> generatedNumbers = new HashSet<>();

    @Getter
    private static final CarNumber firstCarNumber = CarNumber.builder()
            .id(UUID.randomUUID())
            .frontSeries("A")
            .registrationNumber("000")
            .backSeries("AA")
            .region(REGION)
            .country(COUNTRY)
            .createdAt(LocalDateTime.now())
            .build();

    private static final Queue<CarNumber> offset = new ArrayDeque<>();

    static {
        offset.add(firstCarNumber);
    }

    public CarNumber next() {
        CarNumber previousCarNumber = offset.poll();
        CarNumber nextCarNumber = null;

        if (previousCarNumber != null) {
            do {
                nextCarNumber = generateNextCarNumber(previousCarNumber);
            } while (generatedNumbers.contains(nextCarNumber));
            generatedNumbers.add(nextCarNumber);
            offset.add(nextCarNumber);
        }
        return nextCarNumber;
    }

    public CarNumber random() {
        offset.poll();
        CarNumber carNumber;

        do {
            carNumber = generateRandomCarNumber();
        } while (generatedNumbers.contains(carNumber));

        offset.add(carNumber);
        return carNumber;
    }

    private CarNumber generateNextCarNumber(CarNumber previousCarNumber) {
        int digit = Integer.parseInt(previousCarNumber.getRegistrationNumber()) + 1;

        if (digit < 1000) {
            return CarNumber.builder()
                    .id(UUID.randomUUID())
                    .frontSeries(previousCarNumber.getFrontSeries())
                    .registrationNumber(String.format("%03d", digit))
                    .backSeries(previousCarNumber.getBackSeries())
                    .region(REGION)
                    .country(COUNTRY)
                    .createdAt(LocalDateTime.now())
                    .build();
        } else {
            return null;
           // TODO реализовать генерацию след номера
        }
    }

    private CarNumber generateRandomCarNumber() {
        int digit = random.nextInt(MAX_DIGIT + 1);

        return CarNumber.builder()
                .id(UUID.randomUUID())
                .frontSeries(LETTERS[getRandomIndex()])
                .registrationNumber(String.format("%03d", digit))
                .backSeries(LETTERS[getRandomIndex()] + LETTERS[getRandomIndex()])
                .region(REGION)
                .country(COUNTRY)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private int getRandomIndex() {
        return random.nextInt(MAX_LETTER_INDEX + 1);
    }

    public void setOffset(CarNumber carNumber) {
        offset.add(carNumber);
    }

    public void removeOffset() {
        offset.poll();
    }
}