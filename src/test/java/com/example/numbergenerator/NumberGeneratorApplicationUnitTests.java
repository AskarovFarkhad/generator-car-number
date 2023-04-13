package com.example.numbergenerator;

import com.example.numbergenerator.model.CarNumber;
import com.example.numbergenerator.util.GeneratorCarNumber;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static com.example.numbergenerator.util.Constants.REGEX_AUTO_NUMBER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NumberGeneratorApplicationUnitTests {

    @BeforeEach
    void beforeEach() {
        GeneratorCarNumber.removeOffset();
    }

    @Test
    @DisplayName(value = "Generate next car number if don't have previous element")
    void GeneratorCarNumber_GenerateNextCarNumber_ShouldReturnRandomNumberIfDontHavePreviousElement() {
        CarNumber carNumber = GeneratorCarNumber.next();
        assertNotNull(carNumber);
        assertTrue(carNumber.toString().matches(REGEX_AUTO_NUMBER));
    }

    @Test
    @DisplayName(value = "Generate next car number: after -> C399BA 116 RUS")
    void GeneratorCarNumber_GenerateNextCarNumber_ShouldReturnDifferentRegistrationNumber() {
        GeneratorCarNumber.setOffset(CarNumber.builder()
                .series("СВА")
                .registrationNumber("399")
                .build());

        CarNumber carNumber = GeneratorCarNumber.next();
        assertNotNull(carNumber);
        assertEquals("С400ВА 116 RUS", carNumber.toString());
    }

    @Test
    @DisplayName(value = "Generate next car number: after -> C089BA 116 RUS")
    void GeneratorCarNumber_GenerateNextCarNumber_ShouldReturnDifferentRegistrationNumberWithZeroBegin() {
        GeneratorCarNumber.setOffset(CarNumber.builder()
                .series("СВА")
                .registrationNumber("089")
                .build());

        CarNumber carNumber = GeneratorCarNumber.next();
        assertNotNull(carNumber);
        assertEquals("С090ВА 116 RUS", carNumber.toString());
    }

    @Test
    @Disabled
    @DisplayName(value = "Generate next car number: after -> C999BA 116 RUS")
    void GeneratorCarNumber_GenerateNextCarNumber_ShouldReturnDifferentRegistrationNumberAndSeries() {
        GeneratorCarNumber.setOffset(CarNumber.builder()
                .series("СВА")
                .registrationNumber("999")
                .build());

        CarNumber carNumber = GeneratorCarNumber.next();
        assertNotNull(carNumber);
        assertEquals("С000ВВ 116 RUS", carNumber.toString());
    }

    @ParameterizedTest
    @MethodSource("argsGetRandomCarNumber")
    @DisplayName(value = "Generate random car numbers")
    void GeneratorCarNumber_GenerateRandomCarNumber_ShouldReturnRandomAndCorrectCarNumber(CarNumber carNumber) {
        assertNotNull(carNumber);
        assertTrue(carNumber.toString().matches(REGEX_AUTO_NUMBER));
    }

    private Stream<CarNumber> argsGetRandomCarNumber() {
        return Stream
                .generate(GeneratorCarNumber::random)
                .limit(25);
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
}