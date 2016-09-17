package com.prima.pricer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.prima.pricer.interfaces.ApplicationFacade;

public class App {

	protected final static Logger logger = LogManager.getLogger(App.class);
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		logger.info("Initializing Spring context.");
        
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/application-context.xml");
        
        logger.info("Spring context initialized.");

        ApplicationFacade appService = (ApplicationFacade) applicationContext.getBean("applicationService");

        appService.runUpdate();
	}
    //TODO: Read properties config.
    //TODO: Read XML config for each priceBook.
    //TODO: Convert xls to xlsx files.
    //TODO: Loop #1:
    //
}