package com.prima.pricer.model;

import java.util.List;

public class PriceBook {
    private List<PriceBookRecord> records;

    public List<PriceBookRecord> getRecords() {
        return records;
    }

    public void setRecords(List<PriceBookRecord> records) {
        this.records = records;
    }
}