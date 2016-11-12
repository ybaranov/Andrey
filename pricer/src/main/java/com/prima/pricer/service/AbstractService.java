package com.prima.pricer.service;

import com.prima.pricer.interfaces.AvailabilityDeterminerFacade;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractService {
    protected final static Logger logger = LogManager.getRootLogger();

    protected AvailabilityDeterminerFacade availabilityDeterminerSvc;

    protected String composePath(String path) {
        String result = path.replaceAll("prices\\\\p[0-9]{1,2}", "result");
        return appendResultToFileName(result);
    }

    protected String appendResultToFileName(String path) {
        int index = path.lastIndexOf('.');
        if (index != -1) {
            return path.substring(0, index) + "_result" + path.substring(index);
        }
        return path;
    }

    protected String appendTimeStampToFileName(String path) {
        int index = path.lastIndexOf('.');
        if (index != -1) {
            String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
            return path.substring(0, index) + "_" + timestamp + path.substring(index);
        }
        return path;
    }

    protected String appendArchiveToFileName(String path) {
        String result = path.replaceFirst("\\\\result", "\\\\result\\\\archive");
        return result;
    }

    public AvailabilityDeterminerFacade getAvailabilityDeterminerService() {
        return availabilityDeterminerSvc;
    }

    public void setAvailabilityDeterminerService(AvailabilityDeterminerFacade availabilityDeterminerSvc) {
        this.availabilityDeterminerSvc = availabilityDeterminerSvc;
    }
}
