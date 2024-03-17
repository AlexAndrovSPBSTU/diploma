package com.example.diploma.services;

import com.example.diploma.models.Coordinate;
import com.example.diploma.models.Data;
import com.example.diploma.repositories.DataRepository;
import com.example.diploma.util.BurnerNumberDetermination;
import com.example.diploma.util.XlsToProductListParser;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class DataService {
    private final DataRepository dataRepository;
    private final BurnerNumberDetermination burnerNumberDetermination;
    private final XlsToProductListParser productListParser;

    @Autowired
    public DataService(DataRepository dataRepository, BurnerNumberDetermination burnerNumberDetermination, XlsToProductListParser productListParser) {
        this.dataRepository = dataRepository;
        this.burnerNumberDetermination = burnerNumberDetermination;
        this.productListParser = productListParser;
    }

    public List<Coordinate> getData(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return burnerNumberDetermination.getCoordinatesByClusterization(dataRepository.findDataByTimeBetween(dateFrom, dateTo));
    }

    public void saveFileData(MultipartFile file) {
        IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
        List<Data> dataList = productListParser.getProducts(file);
        dataRepository.saveAll(dataList);
    }
}
