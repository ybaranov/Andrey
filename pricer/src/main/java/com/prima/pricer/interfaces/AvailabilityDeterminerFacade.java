package com.prima.pricer.interfaces;

public interface AvailabilityDeterminerFacade {

	boolean determineIsAvailable(String quantity, boolean isAvailabilityOnExistence);
}
