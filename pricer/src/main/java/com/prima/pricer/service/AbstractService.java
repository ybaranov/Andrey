package com.prima.pricer.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.prima.pricer.interfaces.AvailabilityDeterminerFacade;

public abstract class AbstractService {
	protected final static Logger logger = LogManager.getRootLogger();
	
	protected AvailabilityDeterminerFacade availabilityDeterminerSvc;
	
	protected String composePath(String path) {
		String result = path.replaceAll("prices\\\\p[0-9]{1,2}", "result");
		return appedResultToFileName(result);
	}
	
	protected String appedResultToFileName(String path) {
		int index = path.lastIndexOf('.');
		if (index != -1) {
			return path.substring(0, index) + "_result" + path.substring(index);
		}
		return path;
	}
	
	public AvailabilityDeterminerFacade getAvailabilityDeterminerService() {
		return availabilityDeterminerSvc;
	}

	public void setAvailabilityDeterminerService(AvailabilityDeterminerFacade availabilityDeterminerSvc) {
		this.availabilityDeterminerSvc = availabilityDeterminerSvc;
	}
}
