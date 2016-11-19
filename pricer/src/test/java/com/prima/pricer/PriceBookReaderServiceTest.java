package com.prima.pricer;

import com.prima.pricer.interfaces.PriceBookReaderFacade;
import com.prima.pricer.model.ObjectToProcessing;
import com.prima.pricer.model.PriceBook;
import com.prima.pricer.model.PriceBookRecord;
import org.junit.Test;

import java.util.Collection;

public class PriceBookReaderServiceTest extends AbstractTest {
    private static PriceBookReaderFacade priceBookReaderService;

    @SuppressWarnings("resource")
    private static void beforeTest() {
        priceBookReaderService = (PriceBookReaderFacade) applicationContext.getBean("priceBookReaderService");
    }

    @Test
    public void test1() {
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
                    System.out.println("" + priceBookRecord.getRetailPriceMultiplierPercent());
                }
                System.out.println("--------------------------------------------\n");
            }
        }
    }

    @Test
    public void test2() {
//        //arrange
//        beforeClass();
//        beforeTest();
//        ObjectToProcessing objectToProcessing = new ObjectToProcessing();
//        objectToProcessing.setPathToExcel("C:\\andrey\\files\\prices\\p0\\p0.xlsx");
////        objectToProcessing.setPathToExcel("E:\\Dropbox\\Dropbox\\Java-Rep-New\\Andrey\\pricer\\src\\main\\resources\\files\\prices\\p0\\p0.xlsx");
//
//        //act
//        PriceBook result = priceBookReaderService.readExistedResultBook(objectToProcessing);
//
//        //Manual (temporary) verify
//        for (PriceBookRecord priceBookRecord : result.getRecords()) {
//            System.out.println("RowNumber = " + priceBookRecord.getRowNumber());
//            System.out.println("getSupplierId = " + priceBookRecord.getSupplierId());
//            System.out.println("Articul = " + priceBookRecord.getArticul());
//            System.out.println("Name = " + priceBookRecord.getName());
//            System.out.println("Price = " + priceBookRecord.getPrice());
//            System.out.println("Quantity = " + priceBookRecord.getQuantity());
//            if (priceBookRecord.hasRetailPrice()) {
//                System.out.println("" + priceBookRecord.getRetailPriceMultiplierPercent());
//            }
//            System.out.println("--------------------------------------------\n");
//        }
    }
}