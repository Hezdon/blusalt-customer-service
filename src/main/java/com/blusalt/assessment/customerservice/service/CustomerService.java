package com.blusalt.assessment.customerservice.service;

import com.blusalt.assessment.customerservice.dto.CustomerFund;
import com.blusalt.assessment.customerservice.entity.Customer;
import com.blusalt.assessment.customerservice.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    BillingService billingService;

    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public HttpStatus fundCustomer(CustomerFund fund) {

        Optional<Customer> customer = customerRepo.findById(fund.getCustomerId());

        if(customer.isPresent())
            return billingService.sendFund(fund);

        throw new IllegalArgumentException("customer does not exist");
    }
}
