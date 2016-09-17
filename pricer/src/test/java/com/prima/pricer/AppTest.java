package com.prima.pricer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.prima.configuration.dom.Root;
import com.prima.pricer.service.XmlConfigService;

import junit.framework.TestCase;

public class AppTest extends TestCase {
	
	private static XmlConfigService xmlService;
	
	@SuppressWarnings("resource")
	@BeforeClass
	public static void beforeClass() {
		BasicConfigurator.configure();
		logger.info("Initializing Spring context.");
        
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/application-context.xml");
        
        logger.info("Spring context initialized.");

        xmlService = (XmlConfigService) applicationContext.getBean("xmlConfigService");
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
}