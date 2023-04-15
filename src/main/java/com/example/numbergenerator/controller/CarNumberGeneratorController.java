package com.example.numbergenerator.controller;


import com.example.numbergenerator.model.CarNumber;
import com.example.numbergenerator.service.CarNumberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.example.numbergenerator.util.Constants.MAIN_PAGE;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("${spring.app.url}")
public class CarNumberGeneratorController {

    private final CarNumberService service;

    @GetMapping()
    public String getGeneratePage() {
        log.info("Request received main page to generate car number");
        return MAIN_PAGE;
    }

    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    public String getRandomCarNumber(Model model) {
        log.info("Request received to generate random car number");
        CarNumber carNumber = service.getRandomCarNumber();
        model.addAttribute("number", carNumber.toString());
        return MAIN_PAGE;
    }

    @GetMapping("/next")
    @ResponseStatus(HttpStatus.OK)
    public String getNextCarNumber(Model model) {
        log.info("Request received to generate next car number");
        CarNumber carNumber = service.getNextCarNumber();
        model.addAttribute("number", carNumber.toString());
        return MAIN_PAGE;
    }
}