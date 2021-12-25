package com.system.ws.service;


import com.system.ws.domain.entity.Customer;
import com.system.ws.exception.CustomersNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    Customer buyProduct(Customer customer);

    Customer findByCustomerId(String customerid);

    List<Customer> finAllCustomers();

    void deleteCustomerById(String customerId) throws CustomersNotFoundException;

    Page<Customer> findCustomerWithPaging(int offset, int pageSize) throws CustomersNotFoundException;


}
