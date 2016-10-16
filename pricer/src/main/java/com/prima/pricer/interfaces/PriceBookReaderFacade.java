package com.prima.pricer.interfaces;

import java.util.Collection;

import com.prima.pricer.model.ObjectToProcessing;
import com.prima.pricer.model.PriceBook;

public interface PriceBookReaderFacade {
    Collection<PriceBook> getAllBooks();
    PriceBook readExistedResultBook(ObjectToProcessing objectToProcessing);
}