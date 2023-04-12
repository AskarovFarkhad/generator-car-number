package com.example.numbergenerator;

import com.example.numbergenerator.model.CarNumber;
import com.example.numbergenerator.util.GeneratorCarNumber;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NumberGeneratorApplicationTests {

    private final GeneratorCarNumber generatorCarNumber = new GeneratorCarNumber();

    @BeforeEach
    void beforeAll() {
        generatorCarNumber.removeOffset();
    }

    @Test
    @Order(0)
    @DisplayName(value = "Генерация следующего номера после -> C399BA 116 RUS")
    void test0_generatorCarNumber_generateNextCarNumber_shouldReturnDifferentRegistrationNumber() {
        generatorCarNumber.setOffset(
                CarNumber.builder()
                        .frontSeries("C")
                        .registrationNumber("399")
                        .backSeries("BA")
                        .region("116")
                        .country("RUS")
                        .build());
        assertEquals("C400BA 116 RUS", generatorCarNumber.next().toString());
    }

    @Test
    @Order(1)
    @DisplayName(value = "Генерация следующего номера после -> C089BA 116 RUS")
    void test1_generatorCarNumber_generateNextCarNumber_shouldReturnDifferentRegistrationNumber() {
        generatorCarNumber.setOffset(
                CarNumber.builder()
                        .frontSeries("C")
                        .registrationNumber("089")
                        .backSeries("BA")
                        .region("116")
                        .country("RUS")
                        .build());
        assertEquals("C090BA 116 RUS", generatorCarNumber.next().toString());
    }

    @Test
    @Order(2)
    @DisplayName(value = "Генерация следующего номера после -> C999BA 116 RUS")
    void test2_generatorCarNumber_generateNextCarNumber_shouldReturnDifferentRegistrationNumberAndSeries() {
        generatorCarNumber.setOffset(
                CarNumber.builder()
                        .frontSeries("C")
                        .registrationNumber("999")
                        .backSeries("BA")
                        .region("116")
                        .country("RUS")
                        .build());
        assertEquals("C000BB 116 RUS", generatorCarNumber.next().toString());
    }

    @Test
    @Order(3)
    @DisplayName(value = "Генерация случайного номера")
    void test3_generatorCarNumber_generateRandomCarNumber_shouldReturnCorrectResult() {
        CarNumber carNumber = generatorCarNumber.random();

        assertNotNull(carNumber);
        assertEquals(14, carNumber.toString().length());
        assertTrue(carNumber.getRegistrationNumber().length() == 3
                && Integer.parseInt(carNumber.getRegistrationNumber()) < 999);
    }
}