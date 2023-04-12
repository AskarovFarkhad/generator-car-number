package com.example.numbergenerator.repository;


import com.example.numbergenerator.model.CarNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarNumberRepository extends JpaRepository<Long, CarNumber> {

}