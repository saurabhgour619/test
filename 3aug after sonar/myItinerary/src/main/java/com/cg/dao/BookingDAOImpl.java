package com.cg.dao;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.cg.entities.Dining;
import com.cg.entities.DiningRowMapper;
import com.cg.entities.Resort;
import com.cg.entities.ResortRowMapper;

@Repository
public class BookingDAOImpl implements BookingDAO {
	private static final Logger log = LogManager.getLogger("BookingDAOImpl.class");

	@Autowired
	private JdbcTemplate jdbcTemplate;

	Date date = new Date();
	java.sql.Date sqlDate = new java.sql.Date(date.getTime());

	/* Resort queries */
	private static final String SQL_BOOK_RESORT_BY_ID = "INSERT INTO resort_reservation(guest_id_fk, room_type, arrival_date, departure_date,"
			+ " no_of_people, status, created_date, updated_date) values (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_GET_RESORT_DETAILS_BY_GUESTID = "SELECT * FROM resort_reservation WHERE guest_id_fk=?";

	private static final String SQL_GET_RESORT_DETAILS_BY_RESORT_BOOKING_ID = "SELECT * FROM resort_reservation WHERE r_reservation_number = ?";

	private static final String SQL_GET_RESORT_DETAILS_BY_GUEST_ID = "SELECT * FROM resort_reservation where r_reservation_number IN"
			+ "(select max(r_reservation_number) FROM resort_reservation WHERE guest_id_fk=?)";

	private static final String SQL_UPDATE_BOOKED_RESORT_BY_RESORT_BOOKED_ID = "UPDATE resort_reservation SET room_type = ?, arrival_date=?, "
			+ "departure_date=?, no_of_people =?, updated_date=? WHERE r_reservation_number = ?";

	private static final String SQL_CANCEL_RESORT_BY_RESORT_ID = "UPDATE resort_reservation SET status=? WHERE r_reservation_number=?";

	/* Dining queries */
	private static final String SQL_BOOK_DINING_BY_ID = "INSERT INTO dining_reservation(guest_id_fk, dining_type, arrival_date, no_of_people,"
			+ " status, created_date, updated_date) values (?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_GET_DINING_DETAILS_BY_GUESTID = "SELECT * FROM dining_reservation WHERE guest_id_fk=?";

	private static final String SQL_GET_DINING_BY_DINING_BOOKING_ID = "SELECT * FROM dining_reservation WHERE d_reservation_number = ?";

	private static final String SQL_GET_DINING_DETAILS_BY_GUEST_ID = "SELECT * FROM dining_reservation where d_reservation_number IN"
			+ "(select max(d_reservation_number) FROM dining_reservation WHERE guest_id_fk=?)";

	private static final String SQL_UPDATE_BOOKED_DINING_BY_DINING_BOOKED_ID = "UPDATE dining_reservation SET dining_type = ?, arrival_date=?,"
			+ "no_of_people =?, updated_date=? WHERE d_reservation_number = ?";

	private static final String SQL_CANCEL_DINING_BY_DINING_ID = "UPDATE dining_reservation SET status=? WHERE d_reservation_number=?";

	/* Booking Resort */
	@Override
	public Resort bookResort(Resort resort, long guestID) {
		log.info("BookingDAOImpl:Executing bookResort method.");
		try {
			resort.setGuestID(guestID);
			resort.setStatus("booked");
			resort.setCreatedDate(date);
			resort.setUpdatedDate(date);

			Object[] params = { resort.getGuestID(), resort.getRoomType(), resort.getArrivalDate(),
					resort.getDepartureDate(), resort.getNoOfPeople(), resort.getStatus(), resort.getCreatedDate(),
					resort.getUpdatedDate() };

			jdbcTemplate.update(SQL_BOOK_RESORT_BY_ID, params);
			log.info("BookingDAOImpl:Resort Booked.");
		} catch (DataAccessException e) {
			log.warn("BookingDAOImpl:Resort booking failed. No guest found with guestID=" + guestID
					+ "DataAccessException occured in bookResort method" + e.getStackTrace());
			return null;
		}
		// Retrieving resort booking
		return getResortbyGuestID(guestID);
	}

	public Resort getResortbyGuestID(long guestID) {
		Resort resort;
		try {
			resort = jdbcTemplate.queryForObject(SQL_GET_RESORT_DETAILS_BY_GUEST_ID, new Object[] { guestID },
					new ResortRowMapper());
			log.info("BookingDAOImpl:Resort Entity Returned.");
		} catch (DataAccessException e) {
			log.warn(
					"BookingDAOImpl:Getting Resort booked details failed. DataAccessException occured in bookResort method "
							+ e.getStackTrace());
			return null;
		}
		return resort;
	}

	@Override
	public Resort updateBookResort(Resort resort, long resortBookingId) {
		log.info("BookingDAOImpl:Executing updateBookResort.");
		try {
			resort.setrReservationNumber(resortBookingId);
			resort.setUpdatedDate(date);
			Object[] params = { resort.getRoomType(), resort.getArrivalDate(), resort.getDepartureDate(),
					resort.getNoOfPeople(), resort.getUpdatedDate(), resort.getrReservationNumber() };

			jdbcTemplate.update(SQL_UPDATE_BOOKED_RESORT_BY_RESORT_BOOKED_ID, params);
			log.info("BookingDAOImpl:Resort Updated.");
		} catch (DataAccessException e) {
			log.warn(
					"BookingDAOImpl:updating booked resort failed. DataAccessException occured in updateBookResort method "
							+ e.getStackTrace());
			return null;
		}
		return getResortByResortBookingID(resortBookingId);
	}

	@Override
	public Resort cancelBookResort(long resortBookingId) {
		log.info("BookingDAOImpl:Executing cancelBookResort");
		String status = "cancelled";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource().addValue("status", status)
					.addValue("r_reservation_number", resortBookingId);
			jdbcTemplate.update(SQL_CANCEL_RESORT_BY_RESORT_ID, params);
			log.info("BookingDAOImpl:Resort Booking Cancelled ");
		} catch (DataAccessException e) {
			log.warn(
					"BookingDAOImpl:cancelling booked resort failed. DataAccessException occured in cancelBookResort method"
							+ e.getStackTrace());
			return null;
		}
		return getResortByResortBookingID(resortBookingId);
	}

	@Override
	public Dining bookDining(Dining dining, long guestID) {
		log.info("BookingDAOImpl:Executing bookDining method.");
		try {
			dining.setGuestID(guestID);
			dining.setStatus("booked");
			dining.setCreatedDate(date);
			dining.setUpdatedDate(date);

			Object[] params = { dining.getGuestID(), dining.getDiningType(), dining.getArrivalDate(),
					dining.getNoOfPeople(), dining.getStatus(), dining.getCreatedDate(), dining.getUpdatedDate() };

			jdbcTemplate.update(SQL_BOOK_DINING_BY_ID, params);
			log.info("BookingDAOImpl:Dining Booked.");
		} catch (DataAccessException e) {
			log.warn("BookingDAOImpl:booked dining failed. DataAccessException occured in bookDining method"
					+ e.getStackTrace());
			return null;
		}
		return getDiningbyGuestID(guestID);
	}

	@Override
	public Dining updateBookedDining(Dining dining, long diningBookingId) {
		log.info("BookingDAOImpl:Executing updateBookedDining.");
		dining.setdReservationNumber(diningBookingId);
		dining.setUpdatedDate(date);

		Object[] params = { dining.getDiningType(), dining.getArrivalDate(), dining.getNoOfPeople(),
				dining.getUpdatedDate(), dining.getdReservationNumber() };

		try {
			jdbcTemplate.update(SQL_UPDATE_BOOKED_DINING_BY_DINING_BOOKED_ID, params);
			log.info("BookingDAOImpl:Dining Updated.");
		} catch (DataAccessException e) {
			log.warn(
					"BookingDAOImpl:updating booked dining failed.DataAccessException occured in updateBookedDining method"
							+ e.getStackTrace());
			return null;
		}
		return getDiningByDiningBookingID(diningBookingId);
	}

	@Override
	public Dining cancelBookedDining(long diningBookingId) {
		log.info("BookingDAOImpl:Executing cancelBookedDining");
		String status = "cancelled";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource().addValue("status", status)
					.addValue("d_reservation_number", diningBookingId);
			jdbcTemplate.update(SQL_CANCEL_DINING_BY_DINING_ID, params);
			log.info("BookingDAOImpl:Resort Cancelled.");
		} catch (DataAccessException e) {
			log.warn(
					"BookingDAOImpl:cancelling booked dining failed.DataAccessException occured in cancelBookedDining method"
							+ e.getStackTrace());
			return null;
		}
		return getDiningByDiningBookingID(diningBookingId);
	}

	// helper method
	public Resort getResortByResortBookingID(long resortBookingId) {
		Resort resort;
		try {
			resort = jdbcTemplate.queryForObject(SQL_GET_RESORT_DETAILS_BY_RESORT_BOOKING_ID,
					new Object[] { resortBookingId }, new ResortRowMapper());
			log.info("BookingDAOImpl:Resort Entity Returned.");
		} catch (DataAccessException e) {
			log.warn(
					"BookingDAOImpl:getting resort details failed. DataAccessException occured in getResortByResortBookingID method."
							+ e.getStackTrace());
			return null;
		}
		return resort;
	}

	public Dining getDiningByDiningBookingID(long diningBookingId) {
		Dining dining;
		try {
			dining = jdbcTemplate.queryForObject(SQL_GET_DINING_BY_DINING_BOOKING_ID, new Object[] { diningBookingId },
					new DiningRowMapper());
			log.info("BookingDAOImpl:Dining Entity by dining booked id is Returned.");
		} catch (DataAccessException e) {
			log.warn(
					"BookingDAOImpl:getting dining detail failed.DataAccessException occured in getDiningByDiningBookingID method."
							+ e.getStackTrace());
			return null;
		}
		return dining;
	}

	public Dining getDiningbyGuestID(long guestID) {
		Dining dining;
		try {
			dining = jdbcTemplate.queryForObject(SQL_GET_DINING_DETAILS_BY_GUEST_ID, new Object[] { guestID },
					new DiningRowMapper());
			log.info("BookingDAOImpl:Dining Entity by guestID is Returned.");
		} catch (DataAccessException e) {
			log.warn(
					"BookingDAOImpl:getting dining detail by guest id failed.DataAccessException occured in getDiningbyGuestID method. "
							+ e.getStackTrace());
			return null;
		}
		return dining;
	}

	@Override
	public List<Resort> getAllResortDetails(long guestID) {
		log.info("BookingDAOImpl:Executing getAllResortDetails method.");
		List<Resort> resort;
		try {
			resort = jdbcTemplate.query(SQL_GET_RESORT_DETAILS_BY_GUESTID, new Object[] { guestID },
					new ResortRowMapper());
			log.info("BookingDAOImpl:Resort Entities Returned.");
		} catch (DataAccessException e) {
			log.warn(
					"BookingDAOImpl:getting all resort details by guest id failed. DataAccessException occured in getAllResortDetails method. "
							+ e.getStackTrace());
			return Collections.emptyList();
		}
		return resort;
	}

	@Override
	public List<Dining> getAllDiningDetails(long guestID) {
		log.info("BookingDAOImpl:Executing getAllDiningDetails method.");
		List<Dining> dining;
		try {
			dining = jdbcTemplate.query(SQL_GET_DINING_DETAILS_BY_GUESTID, new Object[] { guestID },
					new DiningRowMapper());
			log.info("BookingDAOImpl:Dining Entity Returned.");
		} catch (DataAccessException e) {
			log.warn(
					"BookingDAOImpl:getting all dining details by guest id failed. DataAccessException occured in getAllDiningDetails method. "
							+ e.getStackTrace());
			return Collections.emptyList();
		}
		return dining;
	}

	@Override
	public Resort getResort(long rReservationNumber) {
		log.info("BookingDAOImpl:Executing getDining method.");
		Resort resort;
		try {
			resort = jdbcTemplate.queryForObject(SQL_GET_RESORT_DETAILS_BY_RESORT_BOOKING_ID,
					new Object[] { rReservationNumber }, new ResortRowMapper());
			log.debug("query executed" + resort);
		} catch (DataAccessException e) {
			log.warn(
					"BookingDAOImpl: getting resort details by resort reservation id failed. DataAccessException occured in getResort method"
							+ e.getStackTrace());
			return null;
		}
		return resort;
	}

	@Override
	public Dining getDining(long dReservationNumber) {
		log.info("BookingDAOImpl:Executing getDining method.");
		Dining dining;
		try {
			dining = jdbcTemplate.queryForObject(SQL_GET_DINING_BY_DINING_BOOKING_ID,
					new Object[] { dReservationNumber }, new DiningRowMapper());
			log.debug("query executed" + dining);
		} catch (Exception e) {
			log.warn(
					"BookingDAOImpl: getting dining details by dining reservation id failed. DataAccessException occured in getDining method"
							+ e.getStackTrace());
			return null;
		}
		return dining;
	}
}