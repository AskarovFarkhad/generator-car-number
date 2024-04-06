package com.example.numbergenerator.service;


import com.example.numbergenerator.model.CarNumber;

public interface CarNumberService {

    CarNumber getRandomCarNumber();

    CarNumber getNextCarNumber();
}