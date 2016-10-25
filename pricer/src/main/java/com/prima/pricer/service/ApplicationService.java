package com.prima.pricer.service;

import java.util.Collection;

import com.prima.pricer.interfaces.*;
import com.prima.pricer.model.PriceBook;
import com.prima.pricer.model.PriceBookRecord;

public class ApplicationService extends AbstractService implements ApplicationFacade {

	protected ExcelConvertFacade excelConvertService;
	protected PriceBookReaderFacade priceBookReaderService;
	protected PriceBookWriterFacade priceBookWriterService;
	protected CatalogFacade catalogService;

    @Override
	public void runUpdate() {
		logger.info("run update start.");
		Collection<PriceBook> availableBooks = priceBookReaderService.getAllBooks();
		for (final PriceBook book : availableBooks) {
			// TODO yb : read existed result book if it is existed.
			priceBookReaderService.readExistedResultBook(book.getObjectToProcessing());
			// create new if not.
			PriceBook resultBook = new PriceBook();
			processBook(book, resultBook);
			saveBookResult(resultBook);
		}
		//TODO добавить логику обновления
		logger.info("run update end.");
	}
    
    protected void saveBookResult(PriceBook book) {
    	// TODO yb : implement WriterService.
    	// remove when implemented writer Service.
    	priceBookWriterService.writeResultBook(book);
    }
    
    protected void processBook(PriceBook book, PriceBook resultBook) {
    	for (final PriceBookRecord record : book.getRecords()) {
    		final PriceBookRecord existed = resultBook.getRecord(record.getArticul());
    		if (existed != null) {
    			existed.setNew(false);
    			processBookingRecord(record, existed);
    		} else {
    			final PriceBookRecord created = new PriceBookRecord();
    			created.setNew(true);
    			resultBook.addRecord(created);
    			processBookingRecord(record, created);
    		}
    	}
    	resultBook.setObjectToProcessing(book.getObjectToProcessing());
    }
    
    protected void processBookingRecord(PriceBookRecord record, PriceBookRecord newRecord) {
    	// todo : implement update by id (articual id) ->> qnty, price, isAvailable.
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
