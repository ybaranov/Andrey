package com.prima.pricer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.prima.configuration.Settings;

import junit.framework.TestCase;

public class AppTest extends TestCase {
	
	static {
		BasicConfigurator.configure();
	}
	
	protected final static Logger logger = LogManager.getRootLogger();
	
    public void testApp() {
    	assertNotNull(Settings.instanceXml());
    	assertNotNull(Settings.instanceXml().getArticulColumn());
    	assertNotNull(Settings.instanceXml().getProductNameColumn());
    	assertNotNull(Settings.instanceXml().getProductPriceColumn());
    	assertNotNull(Settings.instanceXml().getProductQuantityColumn());
    	assertNotNull(Settings.instanceXml().getSupplierId());
    	assertNotNull(Settings.instanceXml().getRetailPriceMultiplierPercent());
    	assertNotNull(Settings.instanceXml().isHasRetailPrice());
    	logger.info(Settings.instanceXml().getArticulColumn());
    	logger.info(Settings.instanceXml().getProductNameColumn());
    	logger.info(Settings.instanceXml().getProductPriceColumn());
    	logger.info(Settings.instanceXml().getProductQuantityColumn());
    	logger.info(Settings.instanceXml().getSupplierId());
    	logger.info(Settings.instanceXml().getRetailPriceMultiplierPercent());
    	logger.info(Settings.instanceXml().isHasRetailPrice());
    }
}