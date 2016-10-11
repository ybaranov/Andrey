package com.prima.pricer;

import com.prima.pricer.interfaces.CatalogFacade;
import com.prima.pricer.interfaces.PriceBookReaderFacade;
import com.prima.pricer.model.PriceBook;
import com.prima.pricer.model.PriceBookRecord;
import org.junit.Test;

import java.util.Set;

public class PriceBookReaderServiceTest extends AbstractTest {
    private static PriceBookReaderFacade priceBookReaderService;
    private static CatalogFacade catalogService;

    @SuppressWarnings("resource")
    private static void beforeTest() {
        priceBookReaderService = (PriceBookReaderFacade) applicationContext.getBean("priceBookReaderService");
        catalogService = (CatalogFacade) applicationContext.getBean("catalogService");
    }

    @Test
    public void test() {
        //arrange
        beforeClass();
        beforeTest();

        //act
        Set<PriceBook> allBooks = priceBookReaderService.getAllBooks(catalogService);

        //Manual (temporary) verify
        for (PriceBook priceBook : allBooks) {
            System.out.println("\nBook:\n");
            for (PriceBookRecord priceBookRecord : priceBook.getRecords()) {
                System.out.println("RowNumber = " + priceBookRecord.getRowNumber());
                System.out.println("getSupplierId = " + priceBookRecord.getSupplierId());
                System.out.println("Articul = " + priceBookRecord.getArticul());
                System.out.println("Name = " + priceBookRecord.getName());
                System.out.println("Price = " + priceBookRecord.getPrice());
                System.out.println("Quantity = " + priceBookRecord.getQuantity());
                if (priceBookRecord.hasRetailPrice()) {
                    System.out.println("" + priceBookRecord.getArticul());
                }
                System.out.println("--------------------------------------------\n");
            }
        }
    }
}