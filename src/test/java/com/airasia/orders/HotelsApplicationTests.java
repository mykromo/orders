package com.airasia.orders;

import java.util.Date;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.airasia.orders.HotelsApplication;
import com.airasia.orders.model.Customer;
import com.airasia.orders.model.Hotel;
import com.airasia.orders.model.Room;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HotelsApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void testGetAllHotels() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/hotels",
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void testGetHotelById() {
		Hotel hotel = restTemplate.getForObject(getRootUrl() + "/hotels/1", Hotel.class);
		System.out.println(hotel.getName());
		Assert.assertNotNull(hotel);
	}
	
	@Test
	public void testCreateHotel() {
		Room room = new Room();
		room.setName("Deluxe 2");
		room.setNoOfGuests(2);
		
		Customer customer = new Customer();
		customer.setName("Michael Romo");
		customer.setEmail("myk@romo.ph");
		customer.setNumber("0138029833");
		
		Hotel hotel = new Hotel();
		hotel.setName("Test Hotel Name");
		hotel.setCheckIn(new Date());
		hotel.setCheckOut(new Date());
		hotel.setTotalAmount(100.00);
		hotel.setCustomer(customer);
		hotel.setRoom(room);

		ResponseEntity<Hotel> postResponse = restTemplate.postForEntity(getRootUrl() + "/hotels", hotel, Hotel.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}
	
	@Test
	public void testUpdatePost() {
		int id = 1;
		Hotel hotel = restTemplate.getForObject(getRootUrl() + "/hotels/" + id, Hotel.class);
		hotel.setName("New Hotel Name");

		restTemplate.put(getRootUrl() + "/hotels/" + id, hotel);

		Hotel updatedHotel = restTemplate.getForObject(getRootUrl() + "/hotels/" + id, Hotel.class);
		Assert.assertNotNull(updatedHotel);
	}
	
	@Test
	public void testDeletePost() {
		int id = 2;
		Hotel hotel = restTemplate.getForObject(getRootUrl() + "/hotels/" + id, Hotel.class);
		Assert.assertNotNull(hotel);

		restTemplate.delete(getRootUrl() + "/hotels/" + id);

		try {
			hotel = restTemplate.getForObject(getRootUrl() + "/hotels/" + id, Hotel.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
