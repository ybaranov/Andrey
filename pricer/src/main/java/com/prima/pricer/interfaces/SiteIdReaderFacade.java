package com.prima.pricer.interfaces;

import java.util.Map;

public interface SiteIdReaderFacade {

    void readAllProperties();

    Map<String, Map<String, String>> getProperties();
}