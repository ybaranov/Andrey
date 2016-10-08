package com.prima.pricer;

import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class AbstractTest extends TestCase {
    protected final static Logger logger = LogManager.getRootLogger();
    protected static ApplicationContext applicationContext;

    @SuppressWarnings("resource")
    public static void beforeClass() {
        BasicConfigurator.configure();
        logger.info("Initializing Spring context.");

        applicationContext = new ClassPathXmlApplicationContext("/application-context.xml");

        logger.info("Spring context initialized.");
    }
}