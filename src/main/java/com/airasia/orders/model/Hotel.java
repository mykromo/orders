package com.airasia.orders.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "check_in", nullable = false)
    private Date checkIn;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "check_out", nullable = false)
    private Date checkOut;
    
    @Column(name = "total_amount", nullable = false)
    private double  totalAmount;

    @OneToOne(fetch = FetchType.EAGER, 
    		cascade =  CascadeType.ALL) 
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

	@OneToOne(fetch = FetchType.EAGER, 
    		cascade =  CascadeType.ALL) 
	@JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Hotel(String name, Date checkIn, Date checkOut, double totalAmount) {
		this.name = name;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.totalAmount = totalAmount;
	}
	
	public Hotel() {
		
	}
}
