package com.prima.pricer.service;

import com.prima.pricer.interfaces.*;
import com.prima.pricer.model.PriceBook;
import com.prima.pricer.model.PriceBookRecord;

import java.util.Collection;

public class ApplicationService extends AbstractService implements ApplicationFacade {

    protected ExcelConvertFacade excelConvertService;
    protected PriceBookReaderFacade priceBookReaderService;
    protected PriceBookWriterFacade priceBookWriterService;
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
            saveBookResult(resultBook);
            logger.info("Finish working on book " + book.getObjectToProcessing().getPathToExcel() + "\n");
        }
        logger.info("run update end.");
    }

    protected void saveBookResult(PriceBook book) {
        priceBookWriterService.writeResultBook(book);
    }

    protected void processBook(PriceBook book, PriceBook resultBook) {
        for (final PriceBookRecord record : book.getRecords()) {
            final PriceBookRecord existed = resultBook.getRecord(record.getArticul());
            if (existed != null) {
                existed.setNew(false);
                processExistedBookingRecord(record, existed);
            } else {
                final PriceBookRecord created = new PriceBookRecord();
                created.setNew(true);
                processNewBookingRecord(record, created);
                resultBook.addRecord(created);
            }
        }
        resultBook.setObjectToProcessing(book.getObjectToProcessing());
    }

    protected void processExistedBookingRecord(
            PriceBookRecord record,
            PriceBookRecord existedRecord) {
        existedRecord.setQuantity(record.getQuantity());
        existedRecord.setPrice(record.getPrice());
    }

    protected void processNewBookingRecord(PriceBookRecord record, PriceBookRecord newRecord) {
        newRecord.setRowNumber(record.getRowNumber());
        newRecord.setArticul(record.getArticul());
        newRecord.setName(record.getName());
        newRecord.setPrice(record.getPrice());
        newRecord.setQuantity(record.getQuantity());
        newRecord.setRetailPrice(record.hasRetailPrice());
        if (record.hasRetailPrice()) {
            newRecord.setRetailPriceMultiplierPercent(record.getRetailPriceMultiplierPercent());
        } else {
            newRecord.setRetailPriceMultiplierPercent(0);
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
}
