package com.blusalt.assessment.customerservice.service;

import com.blusalt.assessment.customerservice.dto.CustomerFund;
import com.blusalt.assessment.customerservice.entity.Customer;
import com.blusalt.assessment.customerservice.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    private final static String active = "active";
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    @Autowired
    BillingService billingService;

    public Customer saveCustomer(Customer customer) {
        if(customer.getName().isBlank())
            throw new IllegalArgumentException("customer name is empty");

        log.info("saving to the database : {} ", customer);
        return customerRepository.save(customer.withStatus(active));
    }

    public CustomerFund fundCustomer(CustomerFund fund) {

        if(!pattern.matcher(fund.getAmount()).matches())
            throw new IllegalArgumentException("amount is not a valid number");

        Optional<Customer> customer = customerRepository.findById(fund.getCustomerId());
        if(customer.isPresent())
            return billingService.sendFund(fund);

        throw new IllegalArgumentException("customer does not exist");
    }
}
