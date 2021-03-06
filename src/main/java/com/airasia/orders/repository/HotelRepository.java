package com.airasia.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airasia.orders.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {}
