package com.cg.restcontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	private static final Logger log = LogManager.getLogger("BookingController.class");

	@Autowired
	private BookingService bookingService;

	@Autowired
	private BookingErrorResponse bookingErrorResponse;

	@PostMapping("/resort/{guestID}")
	public ResponseEntity<Object> bookResort(@RequestBody Resort resort, @PathVariable long guestID) {
		log.debug("BookingController:Debugging bookResort method");
		log.info("BookingController:Executing Resort Booking for a Guest.");
		Resort bookedResort = bookingService.bookResort(resort, guestID);
		if (bookedResort != null) {
			log.info("Resort Booked for a Guest and bookedResort Entity returned.");
			return new ResponseEntity<>(bookedResort, HttpStatus.CREATED);
		} else {
			log.warn("Resort booking failed for a Guest.");
			bookingErrorResponse.setMessage("Resort Booking failed. Guest not found");
			bookingErrorResponse.setStatus(400);
			return new ResponseEntity<>(bookingErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/resort/get/{rReservationNumber}")
	public ResponseEntity<Object> getResort(@PathVariable long rReservationNumber) {
		log.debug("BookingController:Debugging getResort method");
		log.info("BookingController:	Executing get Resort details");
		Resort getResort = bookingService.getResort(rReservationNumber);
		if (getResort != null) {
			log.info("Resort Booked for a Guest and bookedResort Entity returned.");
			return new ResponseEntity<>(getResort, HttpStatus.OK);
		} else {
			log.warn("Resort booking failed for a Guest.");
			bookingErrorResponse.setMessage("Getting Resort Details failed. Resort id not found");
			bookingErrorResponse.setStatus(400);
			return new ResponseEntity<>(bookingErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/resort/{resortBookingId}")
	public ResponseEntity<Object> updateBookResort(@RequestBody Resort resort, @PathVariable long resortBookingId) {
		log.debug("BookingController:Debugging updateBookResort method");
		log.info("BookingController:Executing updateBookResort for a Guest.");
		Resort updateResort = bookingService.updateBookResort(resort, resortBookingId);
		if (updateResort != null) {
			log.info("Booked Resort for a Guest is updated.");
			return new ResponseEntity<>(updateResort, HttpStatus.CREATED);
		} else {
			log.warn("Resort update failed.Resort booking id not found");
			bookingErrorResponse.setMessage("Resort update failed.Resort booking id not found");
			bookingErrorResponse.setStatus(400);
			return new ResponseEntity<>(bookingErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/cancel/resort/{resortBookingId}")
	public ResponseEntity<Object> cancelBookResort(@PathVariable long resortBookingId) {
		log.debug("BookingController:Debugging cancelBookResort method");
		log.info("BookingController:	Executing cancelBookResort for a Guest.");
		Resort cancelResort = bookingService.cancelBookResort(resortBookingId);
		if (cancelResort != null) {
			log.info("BookingController:	Booked Resort for a Guest is canceled.");
			return new ResponseEntity<>(cancelResort, HttpStatus.OK);
		} else {
			log.warn("BookingController:	Resort cancellation failed.");
			bookingErrorResponse.setMessage("Resort cancellation failed.");
			bookingErrorResponse.setStatus(400);
			return new ResponseEntity<>(bookingErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/* Dining */

	@PostMapping("/dining/{guestID}")
	public ResponseEntity<Object> bookDining(@RequestBody Dining dining, @PathVariable long guestID) {
		log.debug("BookingController:Debugging bookDining method");
		log.info("Executing Dining Booking for a Guest.");
		Dining bookDining = bookingService.bookDining(dining, guestID);
		if (bookDining != null) {
			log.info("BookingController:Dining Booked for a Guest.");
			return new ResponseEntity<>(bookDining, HttpStatus.CREATED);
		} else {
			log.warn("BookingController:Dining not booked for a Guest.");
			bookingErrorResponse.setMessage("Dining Booking failed. Guest not found");
			bookingErrorResponse.setStatus(400);
			return new ResponseEntity<>(bookingErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/dining/get/{dReservationNumber}")
	public ResponseEntity<Object> getDining(@PathVariable long dReservationNumber) {
		log.debug("BookingController:Debugging getDining method");
		log.info("BookingController:Executing get Dining details");
		Dining getDining = bookingService.getDining(dReservationNumber);
		if (getDining != null) {
			log.info("Resort Booked for a Guest and bookedResort Entity returned.");
			return new ResponseEntity<>(getDining, HttpStatus.OK);
		} else {
			log.warn("Resort booking failed for a Guest.");
			bookingErrorResponse.setMessage("Getting Dining Details failed. Dining id not found");
			bookingErrorResponse.setStatus(400);
			return new ResponseEntity<>(bookingErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/dining/{diningBookingId}")
	public ResponseEntity<Object> updateBookedDining(@RequestBody Dining dining, @PathVariable long diningBookingId) {
		log.debug("BookingController:Debugging updateBookedDining method");
		log.info("BookingController:Updating Dining Booking for a Guest.");
		Dining updateDining = bookingService.updateBookedDining(dining, diningBookingId);
		if (updateDining != null) {
			log.info("BookingController:Booked Resort for a Guest is updated.");
			return new ResponseEntity<>(updateDining, HttpStatus.CREATED);
		} else {
			log.warn("BookingController:Booked Dining for a Guest is not updated.");
			bookingErrorResponse.setMessage("Dining update failed.");
			bookingErrorResponse.setStatus(400);
			return new ResponseEntity<>(bookingErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/cancel/dining/{diningBookingId}")
	public ResponseEntity<Object> cancelBookedDining(@PathVariable long diningBookingId) {
		log.debug("BookingController:Debugging cancelBookedDining method");
		log.info("BookingController:Cancelling Dining Booking for a Guest.");
		Dining cancelDining = bookingService.cancelBookedDining(diningBookingId);
		if (cancelDining != null) {
			log.info("BookingController:Cancelled Dining Booking for a Guest.");
			return new ResponseEntity<>(cancelDining, HttpStatus.OK);
		} else {
			log.error("BookingController:Booked Dining for a Guest is not cancelled.");
			bookingErrorResponse.setMessage("Dining cancellation failed.");
			bookingErrorResponse.setStatus(400);
			return new ResponseEntity<>(bookingErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}
}
