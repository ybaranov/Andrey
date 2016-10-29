package com.prima.pricer;

import com.prima.configuration.dom.Root;
import com.prima.pricer.service.ApplicationService;
import com.prima.pricer.service.XmlConfigService;
import org.junit.Test;

public class AppTest extends AbstractTest {

    private static XmlConfigService xmlService;
    private static ApplicationService applicationService;

    @SuppressWarnings("resource")
    public static void beforeTest1() {
        xmlService = (XmlConfigService) applicationContext.getBean("xmlConfigService");
    }

    @SuppressWarnings("resource")
    public static void beforeTest2() {
        applicationService = (ApplicationService) applicationContext.getBean("applicationService");
    }

    @Test
    public void test1() {
        //arrange
        beforeClass();
        beforeTest1();

        //act
        Root root = xmlService.readConfObject("price_conf_0.xml");

        //assert
        assertNotNull(root);
        assertNotNull(root.getArticulColumn());
        assertNotNull(root.getProductNameColumn());
        assertNotNull(root.getProductPriceColumn());
        assertNotNull(root.getProductQuantityColumn());
        assertNotNull(root.getSupplierId());
        assertNotNull(root.getRetailPriceMultiplierPercent());
        assertNotNull(root.isHasRetailPrice());
        logger.info(root.getArticulColumn());
        logger.info(root.getProductNameColumn());
        logger.info(root.getProductPriceColumn());
        logger.info(root.getProductQuantityColumn());
        logger.info(root.getSupplierId());
        logger.info(root.getRetailPriceMultiplierPercent());
        logger.info(root.isHasRetailPrice());
    }

    @Test
    public void test2() {
        //arrange
        beforeClass();
        beforeTest2();

        //act
        applicationService.runUpdate();

        //assert

    }
}