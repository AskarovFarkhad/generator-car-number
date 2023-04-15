package com.example.numbergenerator.util.impl;

import com.example.numbergenerator.model.CarNumber;
import com.example.numbergenerator.util.CarNumberClient;
import com.example.numbergenerator.util.GenerateCarNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import static com.example.numbergenerator.util.CarNumberClient.SERIES_LETTERS;
import static com.example.numbergenerator.util.CarNumberClient.MAX_LETTER_INDEX;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenerateNextCarNumberImpl implements GenerateCarNumber {

    private final CarNumberClient carNumberClient;

    private final GenerateRandomCarNumberImpl generateRandomCarNumber;

    @Override
    public CarNumber generate(Set<CarNumber> generatedNumbers, Queue<CarNumber> offset) {
        CarNumber nextCarNumber;

        try {
            do {
                Optional<CarNumber> optPreviousCarNumber = Optional.ofNullable(offset.poll());

                CarNumber previousCarNumber = optPreviousCarNumber
                        .orElseThrow(() -> new NoSuchElementException("Don't such previous car number"));

                nextCarNumber = generateNextCarNumber(previousCarNumber);

                offset.add(nextCarNumber);
            } while (generatedNumbers.contains(nextCarNumber));

            generatedNumbers.add(nextCarNumber);
            return nextCarNumber;
        } catch (NoSuchElementException e) {
            log.error("NoSuchElementException: {}. Will be generate random car number", e.getMessage());
            carNumberClient.setGenerateCarNumber(generateRandomCarNumber);
            return carNumberClient.execute();
        }
    }

    private CarNumber generateNextCarNumber(CarNumber previousCarNumber) {
        CarNumber carNumber = new CarNumber();
        int updatedRegistrationNumber = Integer.parseInt(previousCarNumber.getRegistrationNumber()) + 1;

        if (updatedRegistrationNumber < 1000) {
            return carNumber
                    .setSeries(previousCarNumber.getSeries())
                    .setRegistrationNumber(String.format("%03d", updatedRegistrationNumber));
        } else {
            char seriesFirstChar = previousCarNumber.getSeries().charAt(0);
            char seriesSecondChar = previousCarNumber.getSeries().charAt(1);
            char seriesThirdChar = previousCarNumber.getSeries().charAt(2);

            carNumber.setRegistrationNumber(String.format("%03d", 0));

            if (seriesThirdChar != SERIES_LETTERS[MAX_LETTER_INDEX]) {
                do {
                    seriesThirdChar++;
                } while (notContainsCharInSeriesLetters(seriesThirdChar));

                return carNumber.setSeries(String.valueOf(seriesFirstChar) + seriesSecondChar + seriesThirdChar);
            } else if (seriesSecondChar != SERIES_LETTERS[MAX_LETTER_INDEX]) {
                do {
                    seriesSecondChar++;
                } while (notContainsCharInSeriesLetters(seriesSecondChar));

                return carNumber.setSeries(String.valueOf(seriesFirstChar) + seriesSecondChar + 'А');
            } else {
                do {
                    seriesFirstChar++;
                } while (notContainsCharInSeriesLetters(seriesFirstChar));

                return carNumber.setSeries(String.valueOf(seriesFirstChar) + 'А' + 'А');
            }
        }
    }

    private boolean notContainsCharInSeriesLetters(char letter) {
        for (char ch : SERIES_LETTERS) if (letter == ch) return false;
        return true;
    }
}