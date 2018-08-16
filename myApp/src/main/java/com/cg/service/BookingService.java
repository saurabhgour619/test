package com.cg.service;

import java.util.List;

import com.cg.entities.Dining;
import com.cg.entities.Resort;

public interface BookingService {
	public Resort bookResort(Resort resort, long guestID);

	public Resort updateBookResort(Resort resort, long resortBookingId);

	public Resort cancelBookResort(long resortBookingId);

	public Dining bookDining(Dining dining, long guestID);

	public Dining updateBookedDining(Dining dining, long diningBookingId);

	public Dining cancelBookedDining(long diningBookingId);

	public List<Resort> getAllResortDetails(long guestID);

	public List<Dining> getAllDiningDetails(long guestID);

}
