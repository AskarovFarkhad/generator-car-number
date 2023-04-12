package com.example.numbergenerator.service;

import com.example.numbergenerator.model.CarNumber;
import com.example.numbergenerator.util.GeneratorCarNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarNumberServiceImpl implements CarNumberService {

    private final GeneratorCarNumber generatorCarNumber;

    @Override
    public CarNumber getRandomCarNumber() {
        return generatorCarNumber.random();
    }

    @Override
    public CarNumber getNextCarNumber() {
        return generatorCarNumber.next();
    }
}