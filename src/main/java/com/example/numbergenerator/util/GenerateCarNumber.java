package com.example.numbergenerator.util;

import com.example.numbergenerator.model.CarNumber;

import java.util.Queue;
import java.util.Set;

public interface GenerateCarNumber {

    CarNumber generate(Set<CarNumber> generatedNumbers, Queue<CarNumber> offset);
}