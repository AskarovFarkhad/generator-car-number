package com.example.numbergenerator.controller;


import com.example.numbergenerator.model.CarNumber;
import com.example.numbergenerator.service.CarNumberService;
import com.example.numbergenerator.util.GeneratorCarNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/public/api/v1/numbers")
public class CarNumberGeneratorController {

    private final CarNumberService carNumberService;

    private final GeneratorCarNumber generatorCarNumber;

    private final String MAIN_PAGE = "generate-page";

    private final String ATTRIBUTE_NAME = "number";

    @GetMapping()
    public String getGeneratePage(Model model) {
        log.info("Request received to generate random car number");
        CarNumber carNumber = generatorCarNumber.getFirstCarNumber();
        model.addAttribute(ATTRIBUTE_NAME, carNumber.toString());
        return MAIN_PAGE;
    }

    @GetMapping("/random")
    public String getRandomCarNumber(Model model) {
        log.info("Request received to generate random car number");
        CarNumber carNumber = carNumberService.getRandomCarNumber();
        model.addAttribute(ATTRIBUTE_NAME, carNumber.toString());
        return MAIN_PAGE;
    }

    @GetMapping("/next")
    public String getNextCarNumber(Model model) {
        log.info("Request received to generate next car number");
        CarNumber carNumber = carNumberService.getNextCarNumber();
        model.addAttribute(ATTRIBUTE_NAME, carNumber.toString());
        return MAIN_PAGE;
    }
}