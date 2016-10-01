package com.prima.pricer.service;

import com.prima.pricer.interfaces.ApplicationFacade;
import com.prima.pricer.interfaces.ExcelConvertFacade;
import com.prima.pricer.interfaces.PriceBookReaderFacade;
import com.prima.pricer.interfaces.PriceBookWriterFacade;

public class ApplicationService extends AbstractService implements ApplicationFacade {

	private ExcelConvertFacade excelConvertService;
	private PriceBookReaderFacade priceBookReaderService;
	private PriceBookWriterFacade priceBookWriterService;
	private CatalogService catalogService;

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
    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Override
	public void runUpdate() {
		logger.info("run update");
		//TODO добавить логику обновления
	}

}
