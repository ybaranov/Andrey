package com.prima.pricer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class App {

	protected final static Logger logger = LogManager.getLogger(App.class);
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		logger.info("1");
	}
}