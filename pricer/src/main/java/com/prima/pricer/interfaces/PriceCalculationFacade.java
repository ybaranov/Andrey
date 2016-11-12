package com.prima.pricer.interfaces;

import com.prima.pricer.model.ObjectToProcessing;

public interface PriceCalculationFacade {

	String calculatePrice(String basePrice, ObjectToProcessing oTo);
}
