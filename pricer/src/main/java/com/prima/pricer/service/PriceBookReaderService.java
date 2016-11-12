package com.prima.pricer.service;

import com.prima.pricer.interfaces.CatalogFacade;
import com.prima.pricer.interfaces.PriceBookReaderFacade;
import com.prima.pricer.model.ObjectToProcessing;
import com.prima.pricer.model.PriceBook;
import com.prima.pricer.model.PriceBookRecord;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class PriceBookReaderService extends AbstractService implements PriceBookReaderFacade {

    protected CatalogFacade catalogFacade;

    @Override
    public Collection<PriceBook> getAllBooks() {
        List<ObjectToProcessing> list = catalogFacade.selectNewObjects();
        Set<PriceBook> result = new HashSet<>();
        for (ObjectToProcessing objectToProcessing : list) {
            PriceBook priceBook = new PriceBook();
            priceBook.setObjectToProcessing(objectToProcessing);
            priceBook.setRecords(readBook(objectToProcessing));
            result.add(priceBook);
        }
        return result;
    }

    protected List<PriceBookRecord> readBook(ObjectToProcessing objectToProcessing) {
        List<PriceBookRecord> result = new ArrayList<>();
        try {
            InputStream inp = new FileInputStream(objectToProcessing.getPathToExcel());

            Workbook workbook = WorkbookFactory.create(inp);
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                PriceBookRecord priceBookRecord = new PriceBookRecord();
                priceBookRecord.setRowNumber(i);
                priceBookRecord.setSupplierId(objectToProcessing.getRoot().getSupplierId());
                try {
                    Row row = sheet.getRow(i);

                    //Articul
                    Cell cell = row.getCell(objectToProcessing.getRoot().getArticulColumn().charAt(0) - 65);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    priceBookRecord.setArticul(cell.getStringCellValue());
                    //Name
                    cell = row.getCell(objectToProcessing.getRoot().getProductNameColumn().charAt(0) - 65);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    priceBookRecord.setName(cell.getStringCellValue());
                    //Price
                    cell = row.getCell(objectToProcessing.getRoot().getProductPriceColumn().charAt(0) - 65);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    priceBookRecord.setPrice(cell.getStringCellValue());
                    //Quantity
                    if (!objectToProcessing.getRoot().isAvailabilityOnExistence()) {
                        cell = row.getCell(objectToProcessing.getRoot().getProductQuantityColumn().charAt(0) - 65);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        priceBookRecord.setQuantity(cell.getStringCellValue());
                    } else {
                        priceBookRecord.setQuantity("+");
                    }
                    //Retail price multiplier percent
                    if (objectToProcessing.getRoot().isHasRetailPrice()) {
                        priceBookRecord.setRetailPriceMultiplierPercent(objectToProcessing.getRoot().getRetailPriceMultiplierPercent());
                    }
                    if (!validateBookRecord(priceBookRecord, objectToProcessing)) {
                    	continue;
                    }
                    result.add(priceBookRecord);
                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (InvalidFormatException e) {
            logger.error(e.getMessage());
        }

        return result;
    }
    
    protected boolean validateBookRecord(
    		PriceBookRecord priceBookRecord, ObjectToProcessing objectToProcessing) {
    	if (StringUtils.isEmpty(priceBookRecord.getArticul())
                || StringUtils.isEmpty(priceBookRecord.getPrice())
                || StringUtils.isEmpty(priceBookRecord.getQuantity())) {
            return false;

        }
        if (!objectToProcessing.getRoot().isHasRetailPrice() && !StringUtils.isNumeric(priceBookRecord.getPrice())
                ) {
            return false;

        }
        return true;
    }

    public void setCatalogService(CatalogFacade catalogFacade) {
        this.catalogFacade = catalogFacade;
    }

    @Override
    public PriceBook readExistedResultBook(ObjectToProcessing objectToProcessing) {
        Properties prop = new Properties();
        InputStream input = null;
        Path path = null;
        try {
            String filename = "application.properties";
            input = CatalogService.class.getClassLoader().getResourceAsStream(filename);
            prop.load(input);
            int start = objectToProcessing.getPathToExcel().lastIndexOf("\\");
            int end = objectToProcessing.getPathToExcel().length();
            String name = objectToProcessing.getPathToExcel().substring(start, end);
            name = appendResultToFileName(name);
            path = Paths.get(prop.getProperty("root.folder") + "result\\" + name);

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
        if (Files.exists(path)) {
            PriceBook result = new PriceBook();
            result.setRecords(new ArrayList<>());
            try {
                InputStream inputStream = new FileInputStream(path.toFile());
                Workbook wb = WorkbookFactory.create(inputStream);
                Sheet sheet = wb.getSheetAt(0);
                for (Row row : sheet) {
                    try {
                        PriceBookRecord record = new PriceBookRecord();
                        record.setRowNumber(row.getRowNum());
                        if (row.getCell(0) != null) {
                            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                            record.setArticul(row.getCell(0).getStringCellValue());
                        }
                        if (row.getCell(1) != null) {
                            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                            record.setName(row.getCell(1).getStringCellValue());
                        }
                        if (row.getCell(2) != null) {
                            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                            record.setPrice(row.getCell(2).getStringCellValue());
                        }
                        if (row.getCell(3) != null) {
                            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                            record.setQuantity(row.getCell(3).getStringCellValue());
                        }
                        if (row.getCell(4) != null) {
                            row.getCell(4).setCellType(Cell.CELL_TYPE_BOOLEAN);
                            record.setRetailPrice(row.getCell(4).getBooleanCellValue());
                        }
                        if (row.getCell(5) != null) {
                            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                            if (record.hasRetailPrice()) {
                                record.setRetailPriceMultiplierPercent(Double.valueOf(row.getCell(5).getStringCellValue()));
                            } else {
                                record.setRetailPriceMultiplierPercent(0d);
                            }
                        }
                        if (row.getCell(6) != null) {
                            row.getCell(6).setCellType(Cell.CELL_TYPE_BOOLEAN);
                            record.setAvailable(row.getCell(6).getBooleanCellValue());
                        }
                        record.setNew(false);

                        if (!validateBookRecord(record, objectToProcessing)) {
                        	continue;
                        }
                        result.addRecord(record);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            return result;
        }
        return null;
    }
}