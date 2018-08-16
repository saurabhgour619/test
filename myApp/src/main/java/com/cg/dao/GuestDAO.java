package com.cg.dao;

import com.cg.entities.Guest;

public interface GuestDAO {

	public Guest registerGuest(Guest guest);

	public Guest validateGuest(String em, String pass);

	public Guest updateGuest(Guest guest, long guestID);

}
