package com.prima.pricer.interfaces;

import com.prima.pricer.model.ObjectToProcessing;

import java.util.List;

public interface CatalogFacade {
    List<ObjectToProcessing> selectNewObjects();
}