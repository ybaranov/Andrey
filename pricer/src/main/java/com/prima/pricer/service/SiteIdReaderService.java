package com.prima.pricer.service;

import com.prima.pricer.interfaces.SiteIdReaderFacade;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SiteIdReaderService extends AbstractService implements SiteIdReaderFacade {

    private Map<String, Map<String, Map<String, String>>> properties = new HashMap<>();

    @Override
    public void readAllProperties() {
        Path path = readPath();
        if (Files.exists(path)) {
            try {
                InputStream inputStream = new FileInputStream(path.toFile());
                Workbook wb = WorkbookFactory.create(inputStream);
                Sheet sheet = wb.getSheetAt(0);
                for (Row row : sheet) {
                    if (row.getCell(0) != null
                            && row.getCell(1) != null
                            && row.getCell(2) != null) {
                        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                        String supplierAndArticul = row.getCell(0).getStringCellValue();
                        int delimiterIndex = supplierAndArticul.indexOf("_");
                        if (delimiterIndex == -1) {
                            continue;
                        }
                        String supplier = supplierAndArticul.substring(0, delimiterIndex);
                        String articul = supplierAndArticul.substring(delimiterIndex + 1);
                        String id = row.getCell(1).getStringCellValue();
                        String name = row.getCell(2).getStringCellValue();
                        Map<String, Map<String, String>> temp = properties.get(supplier);
                        if (temp == null) {
                            temp = new HashMap<>();
                        }
                        Map<String, String> articulAndName = new HashMap<>();
                        articulAndName.put(id, name);
                        temp.put(articul, articulAndName);
                        properties.put(supplier, temp);
                    }
                }
                System.out.println();
                logger.info("Properties from file " + path + " was read successful\n");
            } catch (Exception e) {
                logger.warn("Can't read props.xlsx");
            }
        }
    }

    private Path readPath() {
        Properties prop = new Properties();
        InputStream input = null;
        Path path = null;
        try {
            String filename = "application.properties";
            input = CatalogService.class.getClassLoader().getResourceAsStream(filename);
            prop.load(input);
            path = Paths.get(prop.getProperty("root.folder") + "input\\" + "props.xlsx");
        } catch (IOException e) {
            logger.warn("Exception into reading properties file application.properties");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.warn("Can't close FileInputStream for file application.properties");
                }
            }
        }
        return path;
    }

    @Override
    public Map<String, Map<String, Map<String, String>>> getProperties() {
        return properties;
    }
}