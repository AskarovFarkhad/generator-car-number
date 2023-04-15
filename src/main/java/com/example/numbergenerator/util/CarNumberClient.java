package com.example.numbergenerator.util;

import com.example.numbergenerator.model.CarNumber;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CarNumberClient {

    public static final int MAX_DIGIT;

    public static final char[] SERIES_LETTERS;

    public static final int MAX_LETTER_INDEX;

    @Setter
    private GenerateCarNumber generateCarNumber;

    private final Queue<CarNumber> offset = new ArrayDeque<>();

    private final Set<CarNumber> generatedNumbers = new HashSet<>();

    static {
        SERIES_LETTERS = new char[]{'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'};
        MAX_LETTER_INDEX = SERIES_LETTERS.length - 1;
        MAX_DIGIT = 999;
    }

    public CarNumber execute() {
        return generateCarNumber.generate(generatedNumbers, offset);
    }

    public void setOffset(CarNumber carNumber) {
        offset.add(carNumber);
    }

    public void removeOffset() {
        offset.poll();
    }
}