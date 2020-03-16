package com.airasia.orders.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airasia.orders.exception.ResourceNotFoundException;
import com.airasia.orders.model.Hotel;
import com.airasia.orders.repository.HotelRepository;

@RestController
@RequestMapping("/api/v1")
public class HotelController {
	  @Autowired
	  private HotelRepository hotelRepository;
	  
	  @GetMapping("/hotels")
	  public List<Hotel> getAllHotels() {
	    return hotelRepository.findAll();
	  }
	  
	  @GetMapping("/hotels/{id}")
	  public ResponseEntity<Hotel> getHotelById(@PathVariable(value = "id") Long hotelId)
	      throws Exception {
	    Hotel hotel =
	        hotelRepository
	            .findById(hotelId)
	            .orElseThrow(() -> new ResourceNotFoundException("Hotel not found on :: " + hotelId));
	    return ResponseEntity.ok().body(hotel);
	  }
	  
	  @PostMapping("/hotels")
	  public Hotel createHotel(@Valid @RequestBody Hotel hotel) {		
	    return hotelRepository.save(hotel);
	  }
	  
	  @PutMapping("/hotels/{id}")
	  public ResponseEntity<Hotel> updateHotel(
	      @PathVariable(value = "id") Long hotelId, @Valid @RequestBody Hotel hotelDetails)
	      throws Exception {

	    Hotel hotel = hotelRepository
	            .findById(hotelId)
	            .orElseThrow(() -> new ResourceNotFoundException("Hotel not found on :: " + hotelId));
	    
	    hotel.setName(hotelDetails.getName());
	    hotel.setCheckIn(hotelDetails.getCheckIn());
	    hotel.setCheckOut(hotelDetails.getCheckOut());
	    hotel.setTotalAmount(hotelDetails.getTotalAmount());
	    
	    hotel.getCustomer().setEmail(hotelDetails.getCustomer().getEmail());
	    hotel.getCustomer().setName(hotelDetails.getCustomer().getName());
	    hotel.getCustomer().setNumber(hotelDetails.getCustomer().getNumber());
	    
	    hotel.getRoom().setName(hotelDetails.getRoom().getName());
	    hotel.getRoom().setNoOfGuests(hotelDetails.getRoom().getNoOfGuests());
	    
	    final Hotel updatedHotel = hotelRepository.save(hotel);
	    return ResponseEntity.ok(updatedHotel);
	  }
	  
	  @DeleteMapping("/hotels/{id}")
	  public Map<String, Boolean> deleteHotel(@PathVariable(value = "id") Long hotelId) throws Exception {
	    Hotel hotel = hotelRepository
	            .findById(hotelId)
	            .orElseThrow(() -> new ResourceNotFoundException("Hotel not found on :: " + hotelId));

	    hotelRepository.delete(hotel);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	  }
}
