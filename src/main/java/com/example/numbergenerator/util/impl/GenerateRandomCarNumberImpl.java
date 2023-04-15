package com.example.numbergenerator.util.impl;

import com.example.numbergenerator.model.CarNumber;
import com.example.numbergenerator.util.GenerateCarNumber;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.Random;
import java.util.Set;

import static com.example.numbergenerator.util.CarNumberClient.*;

@Component
public class GenerateRandomCarNumberImpl implements GenerateCarNumber {

    private static final Random random = new Random();

    @Override
    public CarNumber generate(Set<CarNumber> generatedNumbers, Queue<CarNumber> offset) {
        offset.poll();
        CarNumber carNumber;

        do {
            carNumber = generateRandomCarNumber();
        } while (generatedNumbers.contains(carNumber));

        offset.add(carNumber);
        return carNumber;
    }

    private CarNumber generateRandomCarNumber() {
        return CarNumber.builder()
                .series(String.valueOf(
                        SERIES_LETTERS[getRandomIndex()]) +
                        SERIES_LETTERS[getRandomIndex()] +
                        SERIES_LETTERS[getRandomIndex()])
                .registrationNumber(String.format("%03d", random.nextInt(MAX_DIGIT + 1)))
                .build();
    }

    private int getRandomIndex() {
        return random.nextInt(MAX_LETTER_INDEX + 1);
    }
}
