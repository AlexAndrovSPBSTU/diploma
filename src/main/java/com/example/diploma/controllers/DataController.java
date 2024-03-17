package com.example.diploma.controllers;

import com.example.diploma.models.Coordinate;
import com.example.diploma.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class DataController {
    private final DataService dataService;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/coordinates")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Coordinate> getData(
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo) {
        LocalDateTime from = LocalDateTime.parse(dateFrom, formatter);
        LocalDateTime to = LocalDateTime.parse(dateTo, formatter);

        return dataService.getData(from, to);
    }


    @PostMapping("/send")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void send(@RequestParam("file") MultipartFile file) {
        dataService.saveFileData(file);
    }

}
