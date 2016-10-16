package com.prima.pricer;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import com.prima.pricer.interfaces.PriceBookReaderFacade;
import com.prima.pricer.model.PriceBook;
import com.prima.pricer.model.PriceBookRecord;

public class PriceBookReaderServiceTest extends AbstractTest {
    private static PriceBookReaderFacade priceBookReaderService;

    @SuppressWarnings("resource")
    private static void beforeTest() {
        priceBookReaderService = (PriceBookReaderFacade) applicationContext.getBean("priceBookReaderService");
    }

    @Test
    public void test() {
        //arrange
        beforeClass();
        beforeTest();

        //act
        Collection<PriceBook> allBooks = priceBookReaderService.getAllBooks();

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