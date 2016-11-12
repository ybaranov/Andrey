package com.prima.pricer;

import com.prima.pricer.service.SiteIdReaderService;
import org.junit.Test;

import java.util.Map;

public class SiteIdReaderServiceTest extends AbstractTest {
    private static SiteIdReaderService siteIdReaderService;

    @SuppressWarnings("resource")
    private static void beforeTest() {
        siteIdReaderService = (SiteIdReaderService) applicationContext.getBean("siteIdReaderService");
    }

    @Test
    public void test1() {
        //arrange
        beforeClass();
        beforeTest();

        siteIdReaderService.readAllProperties();
        for (Map.Entry<String, Map<String, String>> entry : siteIdReaderService.getProperties().entrySet()) {
            System.out.println("Supplier = " + entry.getKey());
            for (Map.Entry<String, String> entry1 : entry.getValue().entrySet()) {
                System.out.println("articul = " + entry1.getKey() + "\t" + "id = " + entry1.getValue());
            }
        }
    }
}