package com.system.ws.resource;

import com.system.ws.domain.entity.Customer;
import com.system.ws.exception.CustomersNotFoundException;
import com.system.ws.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path={"/","/customer"})
public class CustomerResource {

    private CustomerService customerService;

    @Autowired
    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping("/buyProduct")
    public ResponseEntity<Customer> buyProduct(@RequestBody Customer customer){
          Customer boughtProduct = customerService.buyProduct(customer);
        return new ResponseEntity<>(boughtProduct, HttpStatus.OK);
    }

    @GetMapping("/getCustomer/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId")String customerId){
        Customer customer = customerService.findByCustomerId(customerId);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    @GetMapping("/allCustomer")
    @PreAuthorize("hasAnyAuthority('system:create')")
    public ResponseEntity<Page<Customer>> getAllCustomer(@RequestParam("offset")String offset,
                                                         @RequestParam("pageSize")String pageSize) throws CustomersNotFoundException {
        Page<Customer> customers = customerService.findCustomerWithPaging(Integer.parseInt(offset),Integer.parseInt(pageSize));
        return new ResponseEntity<>(customers,HttpStatus.OK);
    }


    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable("customerId")String customerId) throws CustomersNotFoundException {
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
