package com.cg.bootjdbctemplate;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.dao.BookingDAO;
import com.cg.entities.Dining;
import com.cg.entities.Guest;
import com.cg.entities.Resort;
import com.cg.service.BookingServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingServiceTest {

	@Mock
	private BookingDAO bookingDAO;

	@InjectMocks
	private BookingServiceImpl bookingService;;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	Date date = new Date();
	Guest guest = new Guest();

	@Test
	public void testBookResort() {
		guest.setGuestID(1);
		long guestID = guest.getGuestID();
		Resort resort = new Resort();
		resort.setRoomType("testroom");
		resort.setNoOfPeople(1);
		resort.setArrivalDate(date);
		resort.setDepartureDate(date);

		when(bookingDAO.bookResort(resort, guestID)).thenReturn(resort);
		Resort bookedResortTestObj = bookingService.bookResort(resort, guestID);
		assertNotNull(bookedResortTestObj);
	}

	@Test
	public void testUpdateBookResort() {
		Resort updatedResort = new Resort();
		updatedResort.setRoomType("UpdatedTestroom");
		updatedResort.setNoOfPeople(2);
		updatedResort.setArrivalDate(date);
		updatedResort.setDepartureDate(date);
		updatedResort.setrReservationNumber(500);
		long resortBookingId = updatedResort.getrReservationNumber();

		when(bookingDAO.updateBookResort(updatedResort, resortBookingId)).thenReturn(updatedResort);
		Resort updatedResortTestObj = bookingService.updateBookResort(updatedResort, resortBookingId);
		assertNotNull(updatedResortTestObj);
	}

	@Test
	public void testCancelBookResort() {
		Resort cancelResort = new Resort();
		cancelResort.setrReservationNumber(101);
		long cancelResortID = cancelResort.getrReservationNumber();

		when(bookingDAO.cancelBookResort(cancelResortID)).thenReturn(cancelResort);
		Resort cancelResortTestObj = bookingService.cancelBookResort(cancelResortID);
		assertNotNull(cancelResortTestObj);
	}

	/* Dining */
	@Test
	public void testBookDining() {
		guest.setGuestID(1);
		long guestID = guest.getGuestID();
		Dining dining = new Dining();
		dining.setDiningType("testDining");
		dining.setNoOfPeople(1);
		dining.setArrivalDate(date);

		when(bookingDAO.bookDining(dining, guestID)).thenReturn(dining);
		Dining bookedDiningTestObj = bookingService.bookDining(dining, guestID);
		assertNotNull(bookedDiningTestObj);
	}

	@Test
	public void testUpdateBookDining() {
		Dining updatedDining = new Dining();
		updatedDining.setDiningType("UpdatedTestDining");
		updatedDining.setNoOfPeople(2);
		updatedDining.setArrivalDate(date);
		updatedDining.setdReservationNumber(500);
		long diningBookingId = updatedDining.getdReservationNumber();

		when(bookingDAO.updateBookedDining(updatedDining, diningBookingId)).thenReturn(updatedDining);
		Dining updatedDiningTestObj = bookingService.updateBookedDining(updatedDining, diningBookingId);
		assertNotNull(updatedDiningTestObj);
	}

	@Test
	public void testCancelBookDining() {
		Dining cancelDining = new Dining();
		cancelDining.setdReservationNumber(101);
		long cancelDiningID = cancelDining.getdReservationNumber();

		when(bookingDAO.cancelBookedDining(cancelDiningID)).thenReturn(cancelDining);
		Dining cancelDiningTestObj = bookingService.cancelBookedDining(cancelDiningID);
		assertNotNull(cancelDiningTestObj);
	}

	@Test
	public void testGetAllResortDetails() {
		guest.setGuestID(1);
		long guestID = guest.getGuestID();
		List<Resort> resortList = new ArrayList<>();
		Resort resort = new Resort();
		resort.setRoomType("testroom");
		resort.setNoOfPeople(1);
		resort.setArrivalDate(date);
		resort.setDepartureDate(date);
		resortList.add(resort);
		when(bookingDAO.getAllResortDetails(guestID)).thenReturn(resortList);
		List<Resort> bookedResortTestObj = bookingService.getAllResortDetails(guestID);
		assertNotNull(bookedResortTestObj);
	}

	@Test
	public void testGetAllDiningDetails() {
		guest.setGuestID(1);
		long guestID = guest.getGuestID();
		List<Dining> diningList = new ArrayList<>();
		Dining dining = new Dining();
		dining.setDiningType("testdining");
		dining.setNoOfPeople(1);
		dining.setArrivalDate(date);
		diningList.add(dining);
		when(bookingDAO.getAllDiningDetails(guestID)).thenReturn(diningList);
		List<Dining> bookedDiningTestObj = bookingService.getAllDiningDetails(guestID);
		assertNotNull(bookedDiningTestObj);
	}
}
