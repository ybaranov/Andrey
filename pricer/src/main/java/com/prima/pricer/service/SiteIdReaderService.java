package com.prima.pricer.service;

import com.prima.pricer.interfaces.SiteIdReaderFacade;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SiteIdReaderService extends AbstractService implements SiteIdReaderFacade {

    //Map<suplierId, Map<articul, Pair<siteId, siteName>>>
    private Map<String, Map<String, Pair<String, String>>> properties = new HashMap<>();

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
                        Map<String, Pair<String, String>> temp = properties.get(supplier);
                        if (temp == null) {
                            temp = new HashMap<>();
                        }
                        Pair<String, String> idAndName = new ImmutablePair<>(id, name);
                        temp.put(articul, idAndName);
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

    @Override
    public Pair<String, String> getSiteIdSiteName(String supplierId, String articul) {
        if (properties.containsKey(supplierId)) {
            return properties.get(supplierId).get(articul);
        } else {
            return new ImmutablePair<>("", "");
        }
    }

    @Override
    public Set<String> getExistingArticulsInPropsFile(String supplierId) {
    	if (properties.containsKey(supplierId)) {
    		return properties.get(supplierId).keySet();
    	} else {
    		return new HashSet<>();
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
}