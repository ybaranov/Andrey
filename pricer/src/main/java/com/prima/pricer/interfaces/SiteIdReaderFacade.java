package com.prima.pricer.interfaces;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Set;

public interface SiteIdReaderFacade {

    void readAllProperties();
    
    Pair<String, String> getSiteIdSiteName(String supplierId, String articul);
    
    Set<String> getExistingArticulsInPropsFile(String supplierId);
}