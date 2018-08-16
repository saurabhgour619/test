package com.cg.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DiningRowMapper implements RowMapper<Dining> {
	@Override
	public Dining mapRow(ResultSet rs, int rowNum) throws SQLException {
		Dining dining = new Dining();
		dining.setdReservationNumber(rs.getLong("d_reservation_number"));
		dining.setGuestID(rs.getLong("guest_id_fk"));
		dining.setDiningType(rs.getString("dining_type"));
		dining.setArrivalDate(rs.getDate("arrival_date"));
		dining.setNoOfPeople(rs.getInt("no_of_people"));
		dining.setStatus(rs.getString("status"));
		dining.setCreatedDate(rs.getDate("created_date"));
		dining.setUpdatedDate(rs.getDate("updated_date"));
		return dining;
	}
}