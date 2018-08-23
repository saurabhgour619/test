package com.cg.dao;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cg.entities.Guest;
import com.cg.entities.GuestRowMapper;

@Repository
public class GuestDAOImpl implements GuestDAO {
	private static final Logger log = LogManager.getLogger(GuestDAOImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	Date date = new Date();
	java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	private static final String SQL_INSERT_GUEST = "INSERT INTO guest_profile(email, first_name, last_name, address, phone, password, created_date, updated_date) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE_GUEST = "UPDATE guest_profile SET first_name=?, last_name=?, address=?, phone=?, password=?, updated_date=? "
			+ "WHERE guest_id=?";

	private static final String SQL_GET_GUEST_DETAILS = "SELECT * FROM guest_profile WHERE guest_id in(select max(guest_id) from guest_profile)";

	@Override
	public Guest registerGuest(Guest guest) {
		log.debug("GuestDAOImpl:Debugging registerGuest method");
		log.info("GuestDAOImpl:	Executing Guest Registering .");
		try {
			guest.setCreatedDate(date);
			guest.setUpdatedDate(date);
			Object[] params = { guest.getEmail(), guest.getFirstName(), guest.getLastName(), guest.getAddress(),
					guest.getPhone(), guest.getPassword(), guest.getCreatedDate(), guest.getUpdatedDate() };
			jdbcTemplate.update(SQL_INSERT_GUEST, params);
			log.info("GuestDAOImpl:	New Guest is registered.");
		} catch (Exception e) {
			log.warn("GuestDAOImpl:Registration Failed. Duplicate email.");
			return null;
		}
		return jdbcTemplate.queryForObject(SQL_GET_GUEST_DETAILS, new GuestRowMapper());
	}

	@Override
	public Guest validateGuest(String em, String pass) {
		log.debug("GuestDAOImpl:Debugging validateGuest method");
		log.info("GuestDAOImpl:	validating guest with provided email and password");
		RowMapper<Guest> rowmapper = new GuestRowMapper();
		Guest guest = null;
		try {
			guest = jdbcTemplate.queryForObject("SELECT * FROM guest_profile WHERE email = ? AND password= ?",
					new Object[] { em, pass }, rowmapper);
			log.info("GuestDAOImpl: Guest with provided email and password is found");
		} catch (Exception e) {
			log.warn("GuestDAOImpl:Validation Failed.Guest not found with given email and password.");
			return null;
		}
		return guest;
	}

	@Override
	public Guest updateGuest(Guest guest, long guestID) {
		log.debug("GuestDAOImpl:Debugging updateGuest method");
		log.info("GuestDAOImpl:	Executing Guest Update method .");
		try {
			guest.setGuestID(guestID);
			guest.setUpdatedDate(date);
			Object[] params = { guest.getFirstName(), guest.getLastName(), guest.getAddress(), guest.getPhone(),
					guest.getPassword(), guest.getUpdatedDate(), guest.getGuestID() };
			jdbcTemplate.update(SQL_UPDATE_GUEST, params);
			log.info("GuestDAOImpl: Guest is updated.");
		} catch (Exception e) {
			log.warn("GuestDAOImpl:Updated Failed.");
			return null;
		}
		return jdbcTemplate.queryForObject(SQL_GET_GUEST_DETAILS, new GuestRowMapper());
	}
}