package com.system.ws.service.impl;

import com.system.ws.domain.entity.Customer;
import com.system.ws.domain.entity.Product;
import com.system.ws.exception.CustomersNotFoundException;
import com.system.ws.repository.CustomerRepo;
import com.system.ws.repository.ProductRepo;
import com.system.ws.service.CustomerService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final String NO_CUSTOMERS_MESSAGE = "No customers in";
    private CustomerRepo customerRepo;
    private ProductRepo productRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo,ProductRepo productRepo){
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
    }

    @Override
    public Customer buyProduct(Customer customer) {

        Customer newCustomer =  new Customer();
        newCustomer.setCustomerId(generateCustomerId());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setProducts(customer.getProducts()
                .stream()
                .map(p->{
                    Product product = productRepo.findById(p.getId()).get();
                    return product;
                    })
                .collect(Collectors.toSet()));


        return customerRepo.save(newCustomer);
    }

    @Override
    public Customer findByCustomerId(String customerid) {

        Customer customer = customerRepo.findByCustomerId(customerid);
        if(customer==null){
            throw new UsernameNotFoundException("Customer not found by customer id: "+customerid);
        }
        return customer;
    }

    @Override
    public List<Customer> finAllCustomers() {
        return customerRepo.findAllCustomers();
    }

    @Override
    public void deleteCustomerById(String customerId) throws CustomersNotFoundException {
        Customer customer = customerRepo.findByCustomerId(customerId);
        if(customer==null){
            throw new CustomersNotFoundException("Customer not found by customer id: "+customerId);
        }
        customerRepo.deleteByCustomerId(customer.getCustomerId());
    }

    @Override
    public Page<Customer> findCustomerWithPaging(int offset, int pageSize) throws CustomersNotFoundException {
        Page<Customer> customers = customerRepo.findAll(PageRequest.of(offset,pageSize));

        if(customers == null){
            throw new CustomersNotFoundException(NO_CUSTOMERS_MESSAGE);
        }
        return customers;
    }


    private String generateCustomerId() {
        return RandomStringUtils.randomNumeric(6);
    }
}
