package com.blusalt.assessment.customerservice.service;

import com.blusalt.assessment.customerservice.dto.CustomerFund;
import com.blusalt.assessment.customerservice.entity.Customer;
import com.blusalt.assessment.customerservice.repository.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    private final static String active = "active";
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    @Autowired
    BillingService billingService;

    public Customer saveCustomer(Customer customer) {
        log.info("saving to the database : {} ", customer);
        return customerRepo.save(customer.withStatus(active));
    }

    public HttpStatus fundCustomer(CustomerFund fund) {

        if(!pattern.matcher(fund.getAmount()).matches())
            throw new IllegalArgumentException("amount is not a valid number");

        Optional<Customer> customer = customerRepo.findById(fund.getCustomerId());
        if(customer.isPresent())
            return billingService.sendFund(fund);

        throw new IllegalArgumentException("customer does not exist");
    }
}
