package com.prima.pricer.service;

import com.prima.pricer.interfaces.CatalogFacade;
import com.prima.pricer.interfaces.PriceBookReaderFacade;
import com.prima.pricer.model.ObjectToProcessing;
import com.prima.pricer.model.PriceBook;
import com.prima.pricer.model.PriceBookRecord;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                    cell = row.getCell(objectToProcessing.getRoot().getProductQuantityColumn().charAt(0) - 65);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    priceBookRecord.setQuantity(cell.getStringCellValue());
                    //Retail price multiplier percent
                    if (objectToProcessing.getRoot().isHasRetailPrice()) {
                        priceBookRecord.setRetailPriceMultiplierPercent(objectToProcessing.getRoot().getRetailPriceMultiplierPercent());
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

	public void setCatalogService(CatalogFacade catalogFacade) {
		this.catalogFacade = catalogFacade;
	}

	@Override
	public PriceBook readExistedResultBook(ObjectToProcessing objectToProcessing) {
		// TODO yb : implement.
		return null;
	}
}