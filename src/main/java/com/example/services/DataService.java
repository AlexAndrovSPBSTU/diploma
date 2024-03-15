package com.example.services;

import com.example.models.Coordinate;
import com.example.repositories.DataRepository;
import com.example.util.BurnerNumberDetermination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DataService {
    private final DataRepository dataRepository;
    private final BurnerNumberDetermination burnerNumberDetermination;

    @Autowired
    public DataService(DataRepository dataRepository, BurnerNumberDetermination burnerNumberDetermination) {
        this.dataRepository = dataRepository;
        this.burnerNumberDetermination = burnerNumberDetermination;
    }

    public List<Coordinate> getData() {
        return burnerNumberDetermination.getCoordinates(dataRepository.findAll());
    }
}
