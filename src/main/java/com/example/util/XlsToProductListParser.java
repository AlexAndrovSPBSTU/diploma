package com.example.util;

import com.example.constants.ProjectConstants;
import com.example.models.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class XlsToProductListParser {
    private static final NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

    public List<Data> getProducts() {
        try {
            List<Data> data = parseToProducts(getData());
            return data;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static final SimpleDateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
    private static final SimpleDateFormat OUTPUT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm:ss");

    private static Map<Integer, List<String>> getData() throws IOException, ParseException {
        Map<Integer, List<String>> data = new HashMap<>();

        try (InputStream inputStream = new FileInputStream(ProjectConstants.path)) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                List<String> rowData = new ArrayList<>();

                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        String formattedDate = OUTPUT_DATE_FORMAT.format(date);
                        rowData.add(formattedDate);
                    } else {
                        rowData.add(cell.toString());
                    }
                }

                data.put(row.getRowNum(), rowData);
            }

            workbook.close();
        }

        return data;
    }

    private static List<Data> parseToProducts(Map<Integer, List<String>> data) {
        return data.values()
                .stream()
                .map(cellData -> Data.builder()
                        .time(LocalDateTime.parse(cellData.get(0), formatter))
                        .masutPresure(parseDouble(cellData.get(1)))
                        .masutConsumtion(parseDouble(cellData.get(2)))
                        .steamCapacity(parseDouble(cellData.get(3)))
                        .build())
                .collect(Collectors.toList());
    }

    private static Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.out.println(value);
            return 0.0;
        }
    }
}
