package com.example.numbergenerator;

import com.example.numbergenerator.model.CarNumber;
import com.example.numbergenerator.util.CarNumberClient;
import com.example.numbergenerator.util.impl.GenerateNextCarNumberImpl;
import com.example.numbergenerator.util.impl.GenerateRandomCarNumberImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static com.example.numbergenerator.util.Constants.REGEX_AUTO_NUMBER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NumberGeneratorApplicationUnitTests {

    private final CarNumberClient carNumberClient = new CarNumberClient();

    private final GenerateRandomCarNumberImpl generateRandomCarNumber = new GenerateRandomCarNumberImpl();

    private final GenerateNextCarNumberImpl generateNextCarNumber =
            new GenerateNextCarNumberImpl(carNumberClient, generateRandomCarNumber);


    @BeforeEach
    void beforeEach() {
        carNumberClient.removeOffset();
    }

    @Test
    @DisplayName(value = "Generate next car number if don't have previous element")
    void GeneratorCarNumber_GenerateNextCarNumber_ShouldReturnRandomNumberIfDontHavePreviousElement() {
        carNumberClient.setGenerateCarNumber(generateNextCarNumber);
        CarNumber carNumber = carNumberClient.execute();

        assertNotNull(carNumber);
        assertTrue(carNumber.toString().matches(REGEX_AUTO_NUMBER));
    }

    @ParameterizedTest
    @MethodSource("argsGetPreviousCarNumberAndExpectedCarNumber")
    @DisplayName(value = "Generate next car numbers")
    void GeneratorCarNumber_GenerateNextCarNumber_ShouldReturnCorrectNextNumber(
            String expectedCarNumber, CarNumber previousCarNumber) {

        carNumberClient.setOffset(previousCarNumber);

        carNumberClient.setGenerateCarNumber(generateNextCarNumber);
        CarNumber carNumber = carNumberClient.execute();

        assertNotNull(carNumber);
        assertEquals(expectedCarNumber, carNumber.toString());
    }

    @ParameterizedTest
    @MethodSource("argsGetRandomCarNumber")
    @DisplayName(value = "Generate random car numbers")
    void GeneratorCarNumber_GenerateRandomCarNumber_ShouldReturnRandomAndCorrectCarNumber(CarNumber carNumber) {
        assertNotNull(carNumber);
        assertTrue(carNumber.toString().matches(REGEX_AUTO_NUMBER));
    }

    @Test
    @DisplayName(value = "Check regex")
    void NumberGeneratorApplicationTests_RegexAutoNumber_ShouldReturnNegativeResult() {
        assertFalse("С000ВВ116 RUS".matches(REGEX_AUTO_NUMBER));
        assertFalse("С000ВВ 116RUS".matches(REGEX_AUTO_NUMBER));
        assertFalse("С000ВВ116RUS".matches(REGEX_AUTO_NUMBER));
        assertFalse("С1000ВВ 116 RUS".matches(REGEX_AUTO_NUMBER));
        assertFalse("С000ВВ 116 RU".matches(REGEX_AUTO_NUMBER));
        assertFalse("СA00ВВ 116 RUS".matches(REGEX_AUTO_NUMBER));
    }

    private Stream<CarNumber> argsGetRandomCarNumber() {
        carNumberClient.setGenerateCarNumber(generateRandomCarNumber);
        return Stream
                .generate(carNumberClient::execute)
                .limit(25);
    }

    private Stream<Arguments> argsGetPreviousCarNumberAndExpectedCarNumber() {
        return Stream.of(
                Arguments.of(
                        "С400ВА 116 RUS",
                        CarNumber.builder()
                                .series("СВА")
                                .registrationNumber("399")
                                .build()),

                Arguments.of(
                        "С090ВА 116 RUS",
                        CarNumber.builder()
                                .series("СВА")
                                .registrationNumber("089")
                                .build()),

                Arguments.of(
                        "С000ВВ 116 RUS",
                        CarNumber.builder()
                                .series("СВА")
                                .registrationNumber("999")
                                .build()),

                Arguments.of(
                        "С000ЕА 116 RUS",
                        CarNumber.builder()
                                .series("СВХ")
                                .registrationNumber("999")
                                .build()),

                Arguments.of(
                        "Т000АА 116 RUS",
                        CarNumber.builder()
                                .series("СХХ")
                                .registrationNumber("999")
                                .build()),

                Arguments.of(
                        "А000АА 116 RUS",
                        CarNumber.builder()
                                .series("XХХ")
                                .registrationNumber("999")
                                .build()));
    }
}