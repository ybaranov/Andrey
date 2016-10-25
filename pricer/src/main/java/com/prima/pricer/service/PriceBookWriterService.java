package com.prima.pricer.service;

import com.prima.pricer.interfaces.PriceBookWriterFacade;
import com.prima.pricer.model.PriceBook;

public class PriceBookWriterService extends AbstractService implements PriceBookWriterFacade {

	@Override
	public boolean writeResultBook(PriceBook resultBook) {
		// TODO yb : implement it using resultBook.getObjectToProcessing().
		// return true if successfully written.
		return false;
	}

}
