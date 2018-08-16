package com.cg.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class GuestRowMapper implements RowMapper<Guest> {
	@Override
	public Guest mapRow(ResultSet row, int rowNum) throws SQLException {
		Guest g = new Guest();

		g.setGuestID(row.getLong("guest_id"));
		g.setEmail(row.getString("email"));
		g.setFirstName(row.getString("first_name"));
		g.setLastName(row.getString("last_name"));
		g.setPhone(row.getString("phone"));
		g.setPassword(row.getString("password"));
		g.setAddress(row.getString("address"));
		g.setCreatedDate(row.getDate("created_date"));
		g.setUpdatedDate(row.getDate("created_date"));
		return g;
	}
}