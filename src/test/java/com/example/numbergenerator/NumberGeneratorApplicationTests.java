package com.example.numbergenerator;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static com.example.numbergenerator.util.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("application-test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NumberGeneratorApplicationTests {

    private final MockMvc mockMvc;

    @Value("${spring.app.url}")
    private String PUBLIC_API;

    @Test
    public void shouldReturnRandomCarNumber() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(PUBLIC_API + "/random"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(MAIN_PAGE, Objects.requireNonNull(mvcResult.getModelAndView()).getViewName());
        assertTrue(mvcResult.getModelAndView().getModel().get(ATTRIBUTE_NAME).toString().matches(REGEX_AUTO_NUMBER));
    }

    @Test
    public void shouldReturnNextCarNumber() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(PUBLIC_API + "/next"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(MAIN_PAGE, Objects.requireNonNull(mvcResult.getModelAndView()).getViewName());
        assertTrue(mvcResult.getModelAndView().getModel().get(ATTRIBUTE_NAME).toString().matches(REGEX_AUTO_NUMBER));
    }
}