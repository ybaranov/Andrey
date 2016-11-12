package com.prima.pricer.service;

import com.prima.pricer.interfaces.*;
import com.prima.pricer.model.ObjectToProcessing;
import com.prima.pricer.model.PriceBook;
import com.prima.pricer.model.PriceBookRecord;

import java.util.Collection;

public class ApplicationService extends AbstractService implements ApplicationFacade {

    protected ExcelConvertFacade excelConvertService;
    protected PriceBookReaderFacade priceBookReaderService;
    protected PriceBookWriterFacade priceBookWriterService;
    protected PriceCalculationFacade priceCalculationService;
    protected CatalogFacade catalogService;

    @Override
    public void runUpdate() {
        logger.info("run update start.");
        excelConvertService.prepareAllBooks();
        Collection<PriceBook> availableBooks = priceBookReaderService.getAllBooks();
        for (final PriceBook book : availableBooks) {
            // read existed result book if it is existed, create new if not
            logger.info("Begin working on book " + book.getObjectToProcessing().getPathToExcel());
            PriceBook resultBook = priceBookReaderService.readExistedResultBook(book.getObjectToProcessing());
            if (resultBook != null) {
                priceBookWriterService.writeArchivedResultBook(book);
            } else {
                resultBook = new PriceBook();
            }
            processBook(book, resultBook);
            processMissedBookRecords(book, resultBook);
            saveBookResult(resultBook);
            logger.info("Finish working on book " + book.getObjectToProcessing().getPathToExcel() + "\n");
            //TODO vm: 1. Add calculation retail price from price_multiplier_percent
            //TODO vm: 2. Add id from site into result book
            //TODO vm: 3. Test that not available records change their availability to false
        }
        logger.info("run update end.");
    }

    protected void saveBookResult(PriceBook book) {
        priceBookWriterService.writeResultBook(book);
    }
    
    protected void processMissedBookRecords(PriceBook book, PriceBook resultBook) {
    	book.composeTempMap();
    	for (final PriceBookRecord record : resultBook.getRecords()) {
            final PriceBookRecord existed = book.getRecord(record.getArticul());
            if (existed == null) {
            	record.setAvailable(false);
            }
    	}
    }

    protected void processBook(PriceBook book, PriceBook resultBook) {
        for (final PriceBookRecord record : book.getRecords()) {
            final PriceBookRecord existed = resultBook.getRecord(record.getArticul());
            if (existed != null) {
                existed.setNew(false);
                processExistedBookingRecord(record, existed, book.getObjectToProcessing());
            } else {
                final PriceBookRecord created = new PriceBookRecord();
                created.setNew(true);
                processNewBookingRecord(record, created, book.getObjectToProcessing());
                resultBook.addRecord(created);
            }
        }
        resultBook.setObjectToProcessing(book.getObjectToProcessing());
    }

    protected void processExistedBookingRecord(
            PriceBookRecord record,
            PriceBookRecord existedRecord, ObjectToProcessing oTo) {
        existedRecord.setQuantity(record.getQuantity());
        existedRecord.setPrice(priceCalculationService.calculatePrice(record.getPrice(), oTo));
    }

    protected void processNewBookingRecord(
    		PriceBookRecord record, 
    		PriceBookRecord newRecord, 
    		ObjectToProcessing oTo) {
        newRecord.setRowNumber(record.getRowNumber());
        newRecord.setArticul(record.getArticul());
        newRecord.setName(record.getName());
        newRecord.setPrice(priceCalculationService.calculatePrice(record.getPrice(), oTo));
        newRecord.setQuantity(record.getQuantity());
        newRecord.setRetailPrice(record.hasRetailPrice());
        if (record.hasRetailPrice()) {
            newRecord.setRetailPriceMultiplierPercent(record.getRetailPriceMultiplierPercent());
        } else {
            newRecord.setRetailPriceMultiplierPercent(0d);
        }
        newRecord.setAvailable(record.isAvailable());
    }

    @Override
    public void setExcelConvertService(ExcelConvertFacade facade) {
        this.excelConvertService = facade;
    }

    @Override
    public void setPriceBookReaderService(PriceBookReaderFacade facade) {
        this.priceBookReaderService = facade;
    }

    @Override
    public void setPriceBookWriterService(PriceBookWriterFacade facade) {
        this.priceBookWriterService = facade;
    }

    @Override
    public void setCatalogService(CatalogFacade facade) {
        this.catalogService = facade;
    }

    @Override
	public void setPriceCalculationService(PriceCalculationFacade priceCalculationService) {
		this.priceCalculationService = priceCalculationService;
	}
    
    
}
