package com.prima.pricer.service;

import java.util.HashSet;
import java.util.Set;

import com.prima.pricer.interfaces.AvailabilityDeterminerFacade;

public class AvailabilityDeterminerService implements AvailabilityDeterminerFacade {

	private final static Set<String> PREDEFINED_VALUES = new HashSet<>();
	
	static {
		PREDEFINED_VALUES.add("много");
		PREDEFINED_VALUES.add("мало");
		PREDEFINED_VALUES.add("есть");
		PREDEFINED_VALUES.add("10+");
		PREDEFINED_VALUES.add("+");
		PREDEFINED_VALUES.add("уточнюйте");
	}
	@Override
	public boolean determineIsAvailable(String quantity, boolean isAvailabilityOnExistence) {
		// 1. много мало
		// 2. есть нет
		// 3. int
		// 4. 10+
		// 5. +
		// 6. "уточнюйте"
		// 7. availability_on_existence
		if (isAvailabilityOnExistence) {
			return true;
		}
		try {
			Double iQnty = Double.parseDouble(quantity);
			return iQnty > 0;
		} catch (NumberFormatException ex) {
		}
		if (PREDEFINED_VALUES.contains(quantity)) {
			return true;
		}
		return false;
	}

}