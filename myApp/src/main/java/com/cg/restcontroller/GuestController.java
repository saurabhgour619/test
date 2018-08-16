package com.cg.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entities.Dining;
import com.cg.entities.Guest;
import com.cg.entities.Resort;
import com.cg.error.GuestErrorResponse;
import com.cg.service.BookingService;
import com.cg.service.GuestService;
import com.cg.validator.GuestValidator;

@RestController
@RequestMapping("/guest")
@CrossOrigin(origins = "*")
public class GuestController {

	private static final Logger LOGGER = LogManager.getLogger(GuestController.class);

	@Autowired
	private GuestService guestService;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private GuestErrorResponse guestErrorResponse;

	@Autowired
	private GuestValidator guestValidator;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(guestValidator);
	}

	@PostMapping("/register")
	public ResponseEntity<Object> registerGuest(@RequestBody @Validated Guest guest) {
		LOGGER.debug("GuestController:Debugging createGuest method");
		LOGGER.info("GuestController:Executing Creating Guest Method.");
		Guest checkedGuest = guestService.registerGuest(guest);
		if (checkedGuest != null) {
			LOGGER.info("GuestController:New Guest is registered and Guest Entity is returned as response.");
			return new ResponseEntity<Object>(checkedGuest, HttpStatus.CREATED);
		} else
			LOGGER.warn("GuestController:Guest with same email is already registered.");
		guestErrorResponse.setMessage("Registration failed. Duplicate email found");
		guestErrorResponse.setStatus(400);
		return new ResponseEntity<Object>(guestErrorResponse, HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> loginGuest(@RequestBody Guest guest) {
		LOGGER.debug("GuestController:Debugging loginGuest method");
		LOGGER.info("GuestController:Executing Logging Guest.");
		Guest validGuest;
		validGuest = guestService.validateGuest(guest.getEmail(), guest.getPassword());
		if (validGuest != null) {
			LOGGER.info("Guest is validated and Guest Entity is returned as response.");
			return new ResponseEntity<Object>(validGuest, HttpStatus.CREATED);
		} else {
			LOGGER.warn("Invalid Guest.");
			guestErrorResponse.setMessage("Login failed. Invalid credentials.");
			guestErrorResponse.setStatus(400);
			return new ResponseEntity<Object>(guestErrorResponse, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/{guestID}")
	public ResponseEntity<Object> updateGuest(@RequestBody Guest guest, @PathVariable long guestID) {
		LOGGER.debug("GuestController:Debugging loginGuest method");
		LOGGER.info("GuestController:Executing updateGuest Method.");
		Guest checkedGuest = guestService.updateGuest(guest, guestID);
		if (checkedGuest != null) {
			LOGGER.info("GuestController:Guest is updated and Guest Entity is returned as response.");
			return new ResponseEntity<Object>(checkedGuest, HttpStatus.CREATED);
		} else
			LOGGER.warn("GuestController:Updating Guest Details Failed.");
		guestErrorResponse.setMessage("update failed.");
		guestErrorResponse.setStatus(400);
		return new ResponseEntity<Object>(guestErrorResponse, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/view/{guestID}")
	public List<Object> viewItenarary(@PathVariable long guestID) {
		LOGGER.debug("GuestController:Debugging loginGuest method");
		LOGGER.info("GuestController:	Viewing Guest Booking Details.");
		List<Object> list = new ArrayList<>();
		LOGGER.info("Viewing Guest Resort Booking Details.");
		List<Resort> resort = bookingService.getAllResortDetails(guestID);
		LOGGER.info("Viewing Dinning Guest Booking Details.");
		List<Dining> dining = bookingService.getAllDiningDetails(guestID);
		list.add(resort);
		list.add(dining);
		return list;

	}
}