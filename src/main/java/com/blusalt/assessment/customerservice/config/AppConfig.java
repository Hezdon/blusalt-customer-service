package com.blusalt.assessment.customerservice.config;

import com.blusalt.assessment.customerservice.entity.Customer;
import com.blusalt.assessment.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class AppConfig {
    @Autowired
    CustomerService customerService;

    @PostConstruct
    public void logCustomer(){
        customerService.saveCustomer(new Customer().withName("test name"));
    }
}
