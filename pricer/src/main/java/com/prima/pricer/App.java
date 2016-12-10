package com.prima.pricer;

import com.prima.pricer.interfaces.ApplicationFacade;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.slf4j.LoggerFactory.getLogger;

public class App {

    protected final static Logger logger = getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Initializing Spring context.");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/application-context.xml");

        logger.info("Spring context initialized.");

        ApplicationFacade appService = (ApplicationFacade) applicationContext.getBean("applicationService");

        appService.runUpdate();
    }
}