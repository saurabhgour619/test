package com.cg.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.dao.GuestDAO;
import com.cg.entities.Guest;

@Transactional
@Service
public class GuestServiceImpl implements GuestService {

	private static final Logger LOGGER = LogManager.getLogger(GuestServiceImpl.class);

	@Autowired
	private GuestDAO guestDAO;

	@Override
	public Guest registerGuest(Guest guest) {
		LOGGER.info("GuestServiceImpl: Executing registerGuest method.");
		return guestDAO.registerGuest(guest);
	}

	@Override
	public Guest validateGuest(String em, String pass) {
		LOGGER.info("GuestServiceImpl: Executing validateGuest method.");
		return guestDAO.validateGuest(em, pass);
	}

	@Override
	public Guest updateGuest(Guest guest, long guestID) {
		LOGGER.info("GuestServiceImpl: Executing updateGuest method.");
		return guestDAO.updateGuest(guest, guestID);
	}

}