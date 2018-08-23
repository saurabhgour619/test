package com.cg.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ResortRowMapper implements RowMapper<Resort> {
	@Override
	public Resort mapRow(ResultSet rs, int rowNum) throws SQLException {
		Resort resort = new Resort();
		resort.setrReservationNumber(rs.getLong("r_reservation_number"));
		resort.setGuestID(rs.getLong("guest_id_fk"));
		resort.setRoomType(rs.getString("room_type"));
		resort.setArrivalDate(rs.getDate("arrival_date"));
		resort.setDepartureDate(rs.getDate("departure_date"));
		resort.setNoOfPeople(rs.getInt("no_of_people"));
		resort.setStatus(rs.getString("status"));
		resort.setCreatedDate(rs.getDate("created_date"));
		resort.setUpdatedDate(rs.getDate("updated_date"));
		return resort;
	}
}