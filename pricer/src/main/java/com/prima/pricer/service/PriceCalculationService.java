package com.prima.pricer.service;

import java.math.BigDecimal;

import org.apache.commons.lang3.Validate;

import com.prima.pricer.interfaces.PriceCalculationFacade;
import com.prima.pricer.model.ObjectToProcessing;

public class PriceCalculationService extends AbstractService implements PriceCalculationFacade {

	@Override
	public String calculatePrice(String basePrice, ObjectToProcessing oto) {
		Validate.notNull(oto);
		Validate.notNull(oto.getRoot());
		if (oto.getRoot().isHasRetailPrice()) {
			return basePrice;
		}
		Validate.notNull(oto.getRoot().getRetailPriceMultiplierPercent());
		
		try {
			BigDecimal bDbasePrice = new BigDecimal(basePrice);
			BigDecimal multiplier = new BigDecimal(oto.getRoot().getRetailPriceMultiplierPercent());
			return Integer.toString(bDbasePrice.multiply(multiplier).intValue());
		} catch (NumberFormatException ex) {
			logger.error("Can't cast basePrice {" + basePrice + "} to BigDecimal.", ex);
			return null;
		}
	}

}
