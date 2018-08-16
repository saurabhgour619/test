package com.cg.dao;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cg.entities.Dining;
import com.cg.entities.DiningRowMapper;
import com.cg.entities.Resort;
import com.cg.entities.ResortRowMapper;

@Repository
public class BookingDAOImpl implements BookingDAO {
	private static final Logger LOGGER = LogManager.getLogger("BookingDAOImpl.class");

	@Autowired
	private JdbcTemplate jdbcTemplate;

	Date date = new Date();
	java.sql.Date sqlDate = new java.sql.Date(date.getTime());

	private static final String SQL_BOOK_RESORT_BY_ID = "INSERT INTO resort_reservation(guest_id_fk, room_type, arrival_date, departure_date, no_of_people, status, created_date, updated_date)"
			+ "values (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE_BOOKED_RESORT_BY_REGISTER_ID = "UPDATE resort_reservation SET room_type = ?, arrival_date=?, departure_date=?, "
			+ "no_of_people =?, updated_date=? where r_reservation_number = ?";

	private static final String SQL_BOOK_DINING_BY_ID = "INSERT INTO dining_reservation(guest_id_fk, dining_type, arrival_date, no_of_people, status, created_date, updated_date)"
			+ "values (?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE_BOOKED_DINING_BY_REGISTER_ID = "UPDATE dining_reservation SET dining_type = ?, arrival_date=?,"
			+ "no_of_people =?, updated_date=? where d_reservation_number = ?";

	@Override
	public Resort bookResort(Resort resort, long guestID) {
		LOGGER.info("BookingDAOImpl:Executing bookResort method.");
		try {
			resort.setGuestID(guestID);
			resort.setStatus("booked");
			resort.setCreatedDate(date);
			resort.setUpdatedDate(date);

			Object[] params = { resort.getGuestID(), resort.getRoomType(), resort.getArrivalDate(),
					resort.getDepartureDate(), resort.getNoOfPeople(), resort.getStatus(), resort.getCreatedDate(),
					resort.getUpdatedDate() };

			jdbcTemplate.update(SQL_BOOK_RESORT_BY_ID, params);
			LOGGER.info("BookingDAOImpl:Resort Booked.");
		} catch (Exception e) {
			LOGGER.warn("BookingDAOImpl:Resort booking failed.");
			return null;
		}

		// Retrieving resort booking
		return getResortbyGuestID(guestID);

	}

	@Override
	public Resort updateBookResort(Resort resort, long resortBookingId) {
		LOGGER.info("BookingDAOImpl:Executing updateBookResort.");
		try {
			resort.setrReservationNumber(resortBookingId);
			resort.setUpdatedDate(date);
			Object[] params = { resort.getRoomType(), resort.getArrivalDate(), resort.getDepartureDate(),
					resort.getNoOfPeople(), resort.getUpdatedDate(), resort.getrReservationNumber() };

			jdbcTemplate.update(SQL_UPDATE_BOOKED_RESORT_BY_REGISTER_ID, params);
			LOGGER.info("BookingDAOImpl:Resort Updated.");
		} catch (Exception e) {
			LOGGER.warn("BookingDAOImpl:Resort Update failed.");
			return null;
		}
		return getResortByResortBookingID(resortBookingId);
	}

	@Override
	public Resort cancelBookResort(long resortBookingId) {
		LOGGER.info("BookingDAOImpl:Executing cancelBookResort");
		String status = "cancelled";
		String query = "UPDATE resort_reservation SET status=? where r_reservation_number=" + resortBookingId + "";

		try {
			jdbcTemplate.update(query, status);
			LOGGER.info("BookingDAOImpl:Resort Booking Cancelled ");
		} catch (Exception e) {
			LOGGER.warn("BookingDAOImpl:Cancelling Resort failed.");
			return null;
		}
		return getResortByResortBookingID(resortBookingId);
	}

	@Override
	public Dining bookDining(Dining dining, long guestID) {
		LOGGER.info("BookingDAOImpl:Executing bookDining method.");
		try {
			dining.setGuestID(guestID);
			dining.setStatus("booked");
			dining.setCreatedDate(date);
			dining.setUpdatedDate(date);

			Object[] params = { dining.getGuestID(), dining.getDiningType(), dining.getArrivalDate(),
					dining.getNoOfPeople(), dining.getStatus(), dining.getCreatedDate(), dining.getUpdatedDate() };

			jdbcTemplate.update(SQL_BOOK_DINING_BY_ID, params);
			LOGGER.info("BookingDAOImpl:Dining Booked.");
		} catch (Exception e) {
			LOGGER.warn("BookingDAOImpl:Dining Booking failed.");
			return null;
		}
		return getDiningbyGuestID(guestID);
	}

	@Override
	public Dining updateBookedDining(Dining dining, long diningBookingId) {

		LOGGER.info("BookingDAOImpl:Executing updateBookedDining.");
		dining.setdReservationNumber(diningBookingId);
		dining.setUpdatedDate(date);

		Object[] params = { dining.getDiningType(), dining.getArrivalDate(), dining.getNoOfPeople(),
				dining.getUpdatedDate(), dining.getdReservationNumber() };

		jdbcTemplate.update(SQL_UPDATE_BOOKED_DINING_BY_REGISTER_ID, params);
		LOGGER.info("BookingDAOImpl:Dining Updated.");
		return getDiningByDiningBookingID(diningBookingId);
	}

	@Override
	public Dining cancelBookedDining(long diningBookingId) {
		LOGGER.info("BookingDAOImpl:Executing cancelBookedDining");
		String status = "cancelled";
		String query = "UPDATE dining_reservation SET status=? where d_reservation_number=" + diningBookingId + "";
		try {
			jdbcTemplate.update(query, status);
			LOGGER.info("BookingDAOImpl:Resort Cancelled.");
		} catch (Exception e) {
			LOGGER.warn("BookingDAOImpl:Resort Not Cancelled.");
			return null;
		}
		return getDiningByDiningBookingID(diningBookingId);
	}

	// helper method
	public Resort getResortByResortBookingID(long resortBookingId) {
		String query = "select * from resort_reservation where r_reservation_number=" + resortBookingId + "";
		Resort resort = jdbcTemplate.queryForObject(query, new ResortRowMapper());
		LOGGER.info("BookingDAOImpl:Resort Entity Returned.");
		return resort;
	}

	public Resort getResortbyGuestID(long guestID) {
		Resort resort = jdbcTemplate.queryForObject(
				"select * from resort_reservation where r_reservation_number in(select max(r_reservation_number) from resort_reservation where guest_id_fk="
						+ guestID + ");",
				new ResortRowMapper());
		LOGGER.info("BookingDAOImpl:Resort Entity Returned.");
		return resort;
	}

	public Dining getDiningByDiningBookingID(long diningBookingId) {
		String query = "select * from dining_reservation where d_reservation_number=" + diningBookingId + "";
		Dining dining = jdbcTemplate.queryForObject(query, new DiningRowMapper());
		LOGGER.info("BookingDAOImpl:Dining Entity Returned.");
		return dining;
	}

	public Dining getDiningbyGuestID(long guestID) {
		Dining dining = jdbcTemplate.queryForObject(
				"select * from dining_reservation where d_reservation_number in(select max(d_reservation_number) from dining_reservation where guest_id_fk="
						+ guestID + ");",
				new DiningRowMapper());
		LOGGER.info("BookingDAOImpl:Dining Entity Returned.");
		return dining;
	}

	@Override
	public List<Resort> getAllResortDetails(long guestID) {
		LOGGER.info("BookingDAOImpl:Executing getAllResortDetails method.");
		String query = "SELECT * FROM resort_reservation WHERE guest_id_fk=" + guestID + "";
		List<Resort> resort = jdbcTemplate.query(query, new ResortRowMapper());
		LOGGER.info("BookingDAOImpl:Resort Entity Returned.");
		return resort;
	}

	@Override
	public List<Dining> getAllDiningDetails(long guestID) {
		LOGGER.info("BookingDAOImpl:Executing getAllDiningDetails method.");
		String query = "SELECT * FROM dining_reservation WHERE guest_id_fk=" + guestID + "";
		List<Dining> dining = jdbcTemplate.query(query, new DiningRowMapper());
		LOGGER.info("BookingDAOImpl:Dining Entity Returned.");
		return dining;
	}
}