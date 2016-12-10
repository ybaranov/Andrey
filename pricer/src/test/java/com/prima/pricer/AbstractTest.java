package com.prima.pricer;

import junit.framework.TestCase;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractTest extends TestCase {
    protected final static Logger logger = getLogger(AbstractTest.class);
    protected static ApplicationContext applicationContext;

    @SuppressWarnings("resource")
    public static void beforeClass() {
        logger.info("Initializing Spring context.");

        applicationContext = new ClassPathXmlApplicationContext("/application-context.xml");

        logger.info("Spring context initialized.");
    }
}