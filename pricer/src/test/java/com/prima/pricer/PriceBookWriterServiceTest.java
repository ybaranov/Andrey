package com.prima.pricer;

import com.prima.pricer.interfaces.PriceBookWriterFacade;
import com.prima.pricer.model.ObjectToProcessing;
import com.prima.pricer.model.PriceBook;
import com.prima.pricer.model.PriceBookRecord;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Ignore
public class PriceBookWriterServiceTest extends AbstractTest {
    private static PriceBookWriterFacade priceBookWriterService;
    private static PriceBook priceBook;

    @SuppressWarnings("resource")
    private static void beforeTest() {
        priceBookWriterService = (PriceBookWriterFacade) applicationContext.getBean("priceBookWriterService");

        List<PriceBookRecord> records = new ArrayList<>();

        PriceBookRecord record1 = new PriceBookRecord();
        record1.setArticul("articul1");
        record1.setName("name1");
        record1.setPrice("price1");
        record1.setQuantity("quantity1");
        record1.setRetailPrice(true);
        record1.setRetailPriceMultiplierPercent(10d);
        record1.setAvailable(true);

        PriceBookRecord record2 = new PriceBookRecord();
        record2.setArticul("articul2");
        record2.setName("name2");
        record2.setPrice("price2");
        record2.setQuantity("quantity2");
        record2.setRetailPrice(false);
        record2.setRetailPriceMultiplierPercent(15d);
        record2.setAvailable(false);

        records.add(record1);
        records.add(record2);

        ObjectToProcessing objectToProcessing = new ObjectToProcessing();
        objectToProcessing.setPathToExcel("C:\\andrey\\files\\prices\\p0\\p0.xlsx");
//        objectToProcessing.setPathToExcel("E:\\Dropbox\\Dropbox\\Java-Rep-New\\Andrey\\pricer\\src\\main\\resources\\files\\prices\\p0\\p0.xlsx");

        priceBook = new PriceBook();
        priceBook.setRecords(records);
        priceBook.setObjectToProcessing(objectToProcessing);
    }

    @Test
    public void test() {
        //arrange
        beforeClass();
        beforeTest();

        //act, assert
        assertTrue(priceBookWriterService.writeResultBook(priceBook));
    }
}