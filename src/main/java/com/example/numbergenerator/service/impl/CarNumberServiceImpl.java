package com.example.numbergenerator.service.impl;

import com.example.numbergenerator.model.CarNumber;
import com.example.numbergenerator.repository.CarNumberRepository;
import com.example.numbergenerator.service.CarNumberService;
import com.example.numbergenerator.util.CarNumberClient;
import com.example.numbergenerator.util.impl.GenerateNextCarNumberImpl;
import com.example.numbergenerator.util.impl.GenerateRandomCarNumberImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CarNumberServiceImpl implements CarNumberService {

    private final CarNumberRepository repository;

    private final CarNumberClient carNumberClient;

    private final GenerateNextCarNumberImpl generateNextCarNumber;

    private final GenerateRandomCarNumberImpl generateRandomCarNumber;

    @Override
    @Transactional
    public CarNumber getRandomCarNumber() {
        carNumberClient.setGenerateCarNumber(generateRandomCarNumber);
        CarNumber randomCarNumber = carNumberClient.execute();
        repository.save(randomCarNumber);
        return randomCarNumber;
    }

    @Override
    @Transactional
    public CarNumber getNextCarNumber() {
        carNumberClient.setGenerateCarNumber(generateNextCarNumber);
        CarNumber nextCarNumber = carNumberClient.execute();
        repository.save(nextCarNumber);
        return nextCarNumber;
    }
}