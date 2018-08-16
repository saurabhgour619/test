package com.cg.bootjdbctemplate;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.dao.GuestDAO;
import com.cg.entities.Guest;
import com.cg.service.GuestServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuestServiceTest {
	@Mock
	private GuestDAO guestDAO;

	@InjectMocks
	private GuestServiceImpl guestService;;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	Guest guest = new Guest();

	@Test
	public void testRegisterGuest() {

		guest.setEmail("sg@cg.com");
		guest.setFirstName("Sourabh");
		guest.setLastName("Gour");
		guest.setAddress("Nagpur");
		guest.setPhone("+918983276345");
		guest.setPassword("sourabh");
		when(guestDAO.registerGuest(guest)).thenReturn(guest);

		Guest guestTestObj = guestService.registerGuest(guest);
		assertNotNull(guestTestObj);
	}

	@Test
	public void testValidateGuest() {
		guest.setEmail("sg@cg.com");
		guest.setFirstName("Sourabh");
		guest.setLastName("Gour");
		guest.setAddress("Nagpur");
		guest.setPhone("+918983276345");
		guest.setPassword("sourabh");
		guest.setGuestID(1);

		String em = "sg@cg.com";
		String pass = "sourabh";

		// when(guestDAO.validateGuest(em, pass).thenReturn(guest));
		Guest guestTestObj = guestService.validateGuest(em, pass);
		assertNotNull(guestTestObj);

	}

	@Test
	public void testUpdateGuest() {
		guest.setFirstName("Sourabh");
		guest.setLastName("Gour");
		guest.setAddress("Nagpur");
		guest.setPhone("+918983276345");
		guest.setPassword("abc");
		guest.setGuestID(1);

		when(guestDAO.updateGuest(guest, guest.getGuestID())).thenReturn(guest);

		Guest guestUpdatedTestObj = guestService.updateGuest(guest, 1);
		assertNotNull(guestUpdatedTestObj);
	}
}
