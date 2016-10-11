package com.prima.pricer.interfaces;

import com.prima.pricer.model.PriceBook;

import java.util.Set;

public interface PriceBookReaderFacade {
    Set<PriceBook> getAllBooks(CatalogFacade catalogFacade);
}