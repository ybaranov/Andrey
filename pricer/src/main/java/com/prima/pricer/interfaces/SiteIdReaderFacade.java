package com.prima.pricer.interfaces;

import java.util.Map;
import java.util.Set;

public interface SiteIdReaderFacade {

    void readAllProperties();

    //Map<String, Map<String, Map<String, String>>> getProperties();
    
    String getSiteId(String supplierId, String articul);
    
    Set<String> getExistingArticulsInPropsFile(String supplierId);
}