package com.airasia.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airasia.orders.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {}
