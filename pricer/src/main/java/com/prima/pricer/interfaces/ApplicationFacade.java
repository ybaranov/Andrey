package com.prima.pricer.interfaces;

import com.prima.pricer.service.CatalogService;

public interface ApplicationFacade {

	void setExcelConvertService(ExcelConvertFacade facade);
	
	void setPriceBookReaderService(PriceBookReaderFacade facade);
	
	void setPriceBookWriterService(PriceBookWriterFacade facade);

	void setCatalogService(CatalogFacade facade);
	
	void runUpdate();
	
	void setPriceCalculationService(PriceCalculationFacade priceCalculationService);
	
}
