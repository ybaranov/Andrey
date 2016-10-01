package com.prima.pricer.service;

import com.prima.pricer.interfaces.*;

public class ApplicationService extends AbstractService implements ApplicationFacade {

	private ExcelConvertFacade excelConvertService;
	private PriceBookReaderFacade priceBookReaderService;
	private PriceBookWriterFacade priceBookWriterService;
	private CatalogFacade catalogService;

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
	public void runUpdate() {
		logger.info("run update");
		//TODO добавить логику обновления
	}

}
