package com.example.numbergenerator;

import com.example.numbergenerator.model.CarNumber;
import com.example.numbergenerator.repository.CarNumberRepository;
import com.example.numbergenerator.util.CarNumberClient;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static com.example.numbergenerator.util.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class NumberGeneratorApplicationTests {

    private final MockMvc mockMvc;

    private final CarNumberRepository repository;

    private final CarNumberClient carNumberClient;

    @Value("${spring.app.url}")
    private String PUBLIC_API;

    @Test
    @Order(1)
    void shouldReturnNextCarNumberAndEntityFromDatasource() throws Exception {
        carNumberClient.setOffset(CarNumber.builder()
                .series("СВА")
                .registrationNumber("399")
                .build());

        MvcResult mvcResult = mockMvc.perform(get(PUBLIC_API + "/next"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String carNumberModel =
                Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get(ATTRIBUTE_NAME).toString();

        assertEquals(MAIN_PAGE, Objects.requireNonNull(mvcResult.getModelAndView()).getViewName());
        assertTrue(carNumberModel.matches(REGEX_AUTO_NUMBER));

        CarNumber carNumberEntity = repository.findById(1L).orElse(null);

        assertNotNull(carNumberEntity);
        assertEquals("С400ВА 116 RUS", carNumberEntity.toString());
        assertEquals(carNumberModel, carNumberEntity.toString());
    }

    @Test
    @Order(2)
    void shouldReturnRandomCarNumberAndEntityFromDatasource() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(PUBLIC_API + "/random"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String carNumberModel =
                Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get(ATTRIBUTE_NAME).toString();

        assertEquals(MAIN_PAGE, Objects.requireNonNull(mvcResult.getModelAndView()).getViewName());
        assertTrue(carNumberModel.matches(REGEX_AUTO_NUMBER));

        CarNumber carNumberEntity = repository.findById(2L).orElse(null);

        assertNotNull(carNumberEntity);
        assertEquals(carNumberModel, carNumberEntity.toString());
    }
}