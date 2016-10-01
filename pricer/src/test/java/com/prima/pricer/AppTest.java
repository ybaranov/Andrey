package com.prima.pricer;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.prima.configuration.dom.Root;
import com.prima.pricer.interfaces.CatalogFacade;
import com.prima.pricer.model.ObjectToProcessing;
import com.prima.pricer.service.XmlConfigService;

import junit.framework.TestCase;

public class AppTest extends TestCase {
	
	private static XmlConfigService xmlService;
	private static CatalogFacade catalogService;
	
	@SuppressWarnings("resource")
	@BeforeClass
	public static void beforeClass() {
		BasicConfigurator.configure();
		logger.info("Initializing Spring context.");
        
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/application-context.xml");
        
        logger.info("Spring context initialized.");

        xmlService = (XmlConfigService) applicationContext.getBean("xmlConfigService");
        catalogService = (CatalogFacade) applicationContext.getBean("catalogService");
	}
	
	protected final static Logger logger = LogManager.getRootLogger();
	
	@Test
    public void testApp() {
		beforeClass();
		Root root = xmlService.readConfObject("price_conf_0.xml");
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
	public void test() {
		List<ObjectToProcessing> objs = catalogService.selectNewObjects();
		for (ObjectToProcessing obj : objs) {
			Assert.assertNotNull(obj.getPathToConfigXML());
			logger.info(obj.getPathToConfigXML());
			Assert.assertNotNull(obj.getPathToExcel());
			logger.info(obj.getPathToExcel());
			Assert.assertNotNull(obj.getRoot());
			logger.info(obj.getRoot());
			Root root = obj.getRoot();
			assertNotNull(root);
	    	assertNotNull(root.getArticulColumn());
	    	logger.info(root.getArticulColumn());
	    	assertNotNull(root.getProductNameColumn());
	    	logger.info(root.getProductNameColumn());
	    	assertNotNull(root.getProductPriceColumn());
	    	logger.info(root.getProductPriceColumn());
	    	assertNotNull(root.getProductQuantityColumn());
	    	logger.info(root.getProductQuantityColumn());
	    	assertNotNull(root.getSupplierId());
	    	logger.info(root.getSupplierId());
	    	assertNotNull(root.getRetailPriceMultiplierPercent());
	    	logger.info(root.getRetailPriceMultiplierPercent());
	    	assertNotNull(root.isHasRetailPrice());
	    	logger.info(root.isHasRetailPrice());
		}
	}
}