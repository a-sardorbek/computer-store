package com.system.ws.repository;

import com.system.ws.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {

     Customer findByCustomerId(String customerId);

     @Query(value = "SELECT * FROM customer",nativeQuery = true)
     List<Customer> findAllCustomers();

     void deleteByCustomerId(String customerId);

}
