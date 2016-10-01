package com.prima.pricer.interfaces;

public interface ApplicationFacade {

	void setExcelConvertService(ExcelConvertFacade facade);
	
	void setPriceBookReaderService(PriceBookReaderFacade facade);
	
	void setPriceBookWriterService(PriceBookWriterFacade facade);
	
	void runUpdate();
	
	
}