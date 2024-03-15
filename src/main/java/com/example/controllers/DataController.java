package com.example.controllers;

import com.example.models.Coordinate;
import com.example.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {
    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/coordinates")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Coordinate> getData() {
        return dataService.getData();
    }

}
