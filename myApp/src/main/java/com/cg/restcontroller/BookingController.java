package com.cg.restcontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entities.Dining;
import com.cg.entities.Resort;
import com.cg.error.BookingErrorResponse;
import com.cg.service.BookingService;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "*")
public class BookingController {
	private static final Logger LOGGER = LogManager.getLogger("BookingController.class");

	@Autowired
	private BookingService bookingService;

	@Autowired
	private BookingErrorResponse bookingErrorResponse;

	@PostMapping("/resort/{guestID}")
	public ResponseEntity<Object> bookResort(@RequestBody Resort resort, @PathVariable long guestID) {
		LOGGER.debug("BookingController:Debugging bookResort method");
		LOGGER.info("BookingController:	Executing Resort Booking for a Guest.");
		Resort bookedResort = bookingService.bookResort(resort, guestID);
		if (bookedResort != null) {
			LOGGER.info("Resort Booked for a Guest and bookedResort Entity returned.");
			return new ResponseEntity<Object>(bookedResort, HttpStatus.CREATED);
		} else {
			LOGGER.warn("Resort booking failed for a Guest.");
			bookingErrorResponse.setMessage("Resort Booking failed.");
			bookingErrorResponse.setStatus(400);
			return new ResponseEntity<Object>(bookingErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/resort/{resortBookingId}")
	public ResponseEntity<Object> updateBookResort(@RequestBody Resort resort, @PathVariable long resortBookingId) {
		LOGGER.debug("BookingController:Debugging updateBookResort method");
		LOGGER.info("BookingController:	Executing updateBookResort for a Guest.");
		Resort updateResort = bookingService.updateBookResort(resort, resortBookingId);
		if (updateResort != null) {
			LOGGER.info("Booked Resort for a Guest is updated.");
			return new ResponseEntity<Object>(updateResort, HttpStatus.CREATED);
		} else {
			LOGGER.warn("Booked Resort for a Guest is not updated.");
			bookingErrorResponse.setMessage("Resort update failed.");
			bookingErrorResponse.setStatus(400);
			return new ResponseEntity<Object>(bookingErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/cancel/resort/{resortBookingId}")
	public ResponseEntity<Object> cancelBookResort(@PathVariable long resortBookingId) {
		LOGGER.debug("BookingController:Debugging cancelBookResort method");
		LOGGER.info("BookingController:	Executing cancelBookResort for a Guest.");
		Resort cancelResort = bookingService.cancelBookResort(resortBookingId);
		if (cancelResort != null) {
			LOGGER.info("BookingController:	Booked Resort for a Guest is canceled.");
			return new ResponseEntity<Object>(cancelResort, HttpStatus.OK);
		} else {
			LOGGER.warn("BookingController:	Booked Resort for a Guest is not canceled.");
			bookingErrorResponse.setMessage("Resort cancellation failed.");
			bookingErrorResponse.setStatus(400);
			return new ResponseEntity<Object>(bookingErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/* Dining */

	@PostMapping("/dining/{guestID}")
	public ResponseEntity<Object> bookDining(@RequestBody Dining dining, @PathVariable long guestID) {
		LOGGER.debug("BookingController:Debugging bookDining method");
		LOGGER.info("Executing Dining Booking for a Guest.");
		Dining bookDining = bookingService.bookDining(dining, guestID);
		if (bookDining != null) {
			LOGGER.info("BookingController:	Dining Booked for a Guest.");
			return new ResponseEntity<Object>(bookDining, HttpStatus.CREATED);
		} else {
			LOGGER.warn("BookingController:	Dining not booked for a Guest.");
			bookingErrorResponse.setMessage("Dining Booking failed.");
			bookingErrorResponse.setStatus(400);
			return new ResponseEntity<Object>(bookingErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/dining/{diningBookingId}")
	public ResponseEntity<Object> updateBookedDining(@RequestBody Dining dining, @PathVariable long diningBookingId) {
		LOGGER.debug("BookingController:Debugging updateBookedDining method");
		LOGGER.info("BookingController:Updating Dining Booking for a Guest.");
		Dining updateDining = bookingService.updateBookedDining(dining, diningBookingId);
		if (updateDining != null) {
			LOGGER.info("BookingController:Booked Resort for a Guest is updated.");
			return new ResponseEntity<Object>(updateDining, HttpStatus.CREATED);
		} else {
			LOGGER.warn("BookingController:Booked Dining for a Guest is not updated.");
			bookingErrorResponse.setMessage("Dining update failed.");
			bookingErrorResponse.setStatus(400);
			return new ResponseEntity<Object>(bookingErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/cancel/dining/{diningBookingId}")
	public ResponseEntity<Object> cancelBookedDining(@PathVariable long diningBookingId) {
		LOGGER.debug("BookingController:Debugging cancelBookedDining method");
		LOGGER.info("BookingController:Cancelling Dining Booking for a Guest.");
		Dining cancelDining = bookingService.cancelBookedDining(diningBookingId);
		if (cancelDining != null) {
			LOGGER.info("BookingController:Cancelled Dining Booking for a Guest.");
			return new ResponseEntity<Object>(cancelDining, HttpStatus.OK);
		} else {
			LOGGER.error("BookingController:Booked Dining for a Guest is not cancelled.");
			bookingErrorResponse.setMessage("Dining cancellation failed.");
			bookingErrorResponse.setStatus(400);
			return new ResponseEntity<Object>(bookingErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}
}
