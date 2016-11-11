package com.prima.pricer;

import com.prima.configuration.dom.Root;
import com.prima.pricer.interfaces.CatalogFacade;
import com.prima.pricer.model.ObjectToProcessing;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CatalogServiceTest extends AbstractTest {
    private static CatalogFacade catalogService;

    @SuppressWarnings("resource")
    private static void beforeTest() {
        catalogService = (CatalogFacade) applicationContext.getBean("catalogService");
    }

    @Test
    public void test() {
        //arrange
        beforeClass();
        beforeTest();

        //act
        List<ObjectToProcessing> objects = catalogService.selectNewObjects();

        //assert
        for (ObjectToProcessing objectToProcessing : objects) {
            Assert.assertNotNull(objectToProcessing.getPathToConfigXML());
            logger.info(objectToProcessing.getPathToConfigXML());
            Assert.assertNotNull(objectToProcessing.getPathToExcel());
            logger.info(objectToProcessing.getPathToExcel());
            Assert.assertNotNull(objectToProcessing.getRoot());
            logger.info(objectToProcessing.getRoot());
            Root root = objectToProcessing.getRoot();
            assertNotNull(root);
            assertNotNull(root.getArticulColumn());
            logger.info(root.getArticulColumn());
            assertNotNull(root.getProductNameColumn());
            logger.info(root.getProductNameColumn());
            assertNotNull(root.getProductPriceColumn());
            logger.info(root.getProductPriceColumn());
//            assertNotNull(root.getProductQuantityColumn());
//            logger.info(root.getProductQuantityColumn());
//            assertNotNull(root.getSupplierId());
//            logger.info(root.getSupplierId());
//            assertNotNull(root.getRetailPriceMultiplierPercent());
//            logger.info(root.getRetailPriceMultiplierPercent());
//            assertNotNull(root.isHasRetailPrice());
//            logger.info(root.isHasRetailPrice());
        }
    }
}