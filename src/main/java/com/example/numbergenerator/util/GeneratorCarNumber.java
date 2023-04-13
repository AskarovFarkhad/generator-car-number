package com.example.numbergenerator.util;

import com.example.numbergenerator.model.CarNumber;
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

    private static final Random random = new Random();

    private static final Set<CarNumber> generatedNumbers = new HashSet<>();

    private static final Queue<CarNumber> offset = new ArrayDeque<>();

    public static CarNumber next() {
        Optional<CarNumber> optPreviousCarNumber = Optional.ofNullable(offset.poll());
        CarNumber nextCarNumber;

        try {
            CarNumber previousCarNumber = optPreviousCarNumber
                    .orElseThrow(() -> new NoSuchElementException("Don't such previous car number"));

            do {
                nextCarNumber = generateNextCarNumber(previousCarNumber);
            } while (generatedNumbers.contains(nextCarNumber));

            generatedNumbers.add(nextCarNumber);
            offset.add(nextCarNumber);
            return nextCarNumber;
        } catch (NoSuchElementException e) {
            log.error("NoSuchElementException: {}. Will be generate random car number", e.getMessage());
            return random();
        }
    }

    public static CarNumber random() {
        offset.poll();
        CarNumber carNumber;

        do {
            carNumber = generateRandomCarNumber();
        } while (generatedNumbers.contains(carNumber));

        offset.add(carNumber);
        return carNumber;
    }

    private static CarNumber generateNextCarNumber(CarNumber previousCarNumber) {
        int digit = Integer.parseInt(previousCarNumber.getRegistrationNumber()) + 1;

        if (digit < 1000) {
            return CarNumber.builder()
                    .series(previousCarNumber.getSeries())
                    .registrationNumber(String.format("%03d", digit))
                    .createdAt(LocalDateTime.now())
                    .build();
        } else {
            return null;
            // TODO реализовать генерацию след номера
        }
    }

    private static CarNumber generateRandomCarNumber() {
        int digit = random.nextInt(MAX_DIGIT + 1);

        return CarNumber.builder()
                .series(LETTERS[getRandomIndex()] + LETTERS[getRandomIndex()] + LETTERS[getRandomIndex()])
                .registrationNumber(String.format("%03d", digit))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private static int getRandomIndex() {
        return random.nextInt(MAX_LETTER_INDEX + 1);
    }

    public static void setOffset(CarNumber carNumber) {
        offset.add(carNumber);
    }

    public static void removeOffset() {
        offset.poll();
    }
}