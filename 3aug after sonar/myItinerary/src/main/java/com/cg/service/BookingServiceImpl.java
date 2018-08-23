package com.cg.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.dao.BookingDAO;
import com.cg.entities.Dining;
import com.cg.entities.Resort;

@Transactional
@Service
public class BookingServiceImpl implements BookingService {
	private static final Logger log = LogManager.getLogger("BookingServiceImpl.class");

	@Autowired
	BookingDAO bookingDAO;

	/* Resort Booking Service */
	@Override
	public Resort bookResort(Resort resort, long guestID) {
		log.debug("BookingServiceImpl:Debugging bookResort method");
		log.info("BookingServiceImpl: Executing bookResort method.");
		return bookingDAO.bookResort(resort, guestID);
	}

	@Override
	public Resort updateBookResort(Resort resort, long resortBookingId) {
		log.debug("BookingServiceImpl:Debugging updateBookResort method");
		log.info("BookingServiceImpl: Executing updateBookResort method.");
		return bookingDAO.updateBookResort(resort, resortBookingId);
	}

	@Override
	public Resort cancelBookResort(long resortBookingId) {
		log.debug("BookingServiceImpl:Debugging cancelBookResort method");
		log.info("BookingServiceImpl: Executing cancelBookResort method.");
		return bookingDAO.cancelBookResort(resortBookingId);
	}

	/* Dining Booking Service */
	@Override
	public Dining bookDining(Dining dining, long diningBookingId) {
		log.debug("BookingServiceImpl:Debugging bookDining method");
		log.info("BookingServiceImpl: Executing bookDining method.");
		return bookingDAO.bookDining(dining, diningBookingId);

	}

	@Override
	public Dining updateBookedDining(Dining dining, long diningBookingId) {
		log.debug("BookingServiceImpl:Debugging updateBookedDining method");
		log.info("BookingServiceImpl: Executing updateBookedDining method.");
		return bookingDAO.updateBookedDining(dining, diningBookingId);
	}

	@Override
	public Dining cancelBookedDining(long diningBookingId) {
		log.debug("BookingServiceImpl:Debugging cancelBookedDining method");
		log.info("BookingServiceImpl: Executing cancelBookResort method.");
		return bookingDAO.cancelBookedDining(diningBookingId);
	}

	/* View Booking Service */
	@Override
	public List<Resort> getAllResortDetails(long guestID) {
		log.debug("BookingServiceImpl:Debugging getAllResortDetails method");
		log.info("BookingServiceImpl: Executing getAllResortDetails method.");
		return bookingDAO.getAllResortDetails(guestID);
	}

	@Override
	public List<Dining> getAllDiningDetails(long guestID) {
		log.debug("BookingServiceImpl:Debugging getAllDiningDetails method");
		log.info("BookingServiceImpl: Executing getAllDiningDetails method.");
		return bookingDAO.getAllDiningDetails(guestID);
	}

	@Override
	public Resort getResort(long rReservationNumber) {
		log.debug("BookingServiceImpl:Debugging getResort method");
		log.info("BookingServiceImpl: Executing getResort method.");
		return bookingDAO.getResort(rReservationNumber);
	}

	@Override
	public Dining getDining(long dReservationNumber) {
		log.debug("BookingServiceImpl:Debugging getDining method");
		log.info("BookingServiceImpl: Executing getDining method.");
		return bookingDAO.getDining(dReservationNumber);
	}

}
