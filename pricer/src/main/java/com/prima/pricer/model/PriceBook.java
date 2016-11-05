package com.prima.pricer.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PriceBook {
	
    private Collection<PriceBookRecord> records = new ArrayList<>();
    private Map<String, PriceBookRecord> tempMap = new HashMap<>();
    protected ObjectToProcessing objToP;

    public void addRecord(PriceBookRecord record) {
    	tempMap.put(record.getArticul(), record);
        records.add(record);
    }
    
    public Collection<PriceBookRecord> getRecords() {
        return records;
    }
    
    public PriceBookRecord getRecord(String articul) {
        return tempMap.get(articul);
    }

    public void setRecords(Collection<PriceBookRecord> records) {
    	composeTempMap(records);
        this.records = records;
    }
    
    protected void composeTempMap(Collection<PriceBookRecord> records) {
    	tempMap.clear();
    	for (final PriceBookRecord record : records) {
    		tempMap.put(record.getArticul(), record);
    	}
    }

	public ObjectToProcessing getObjectToProcessing() {
		return objToP;
	}

	public void setObjectToProcessing(ObjectToProcessing objToP) {
		this.objToP = objToP;
	}
}