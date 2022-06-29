package com.blusalt.assessment.customerservice.service.impl;

import com.blusalt.assessment.customerservice.dto.CustomerFund;
import com.blusalt.assessment.customerservice.service.BillingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class BillingServiceImpl implements BillingService {

    @Value("${billing.customer.fund}")
    String billingUrl;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public CustomerFund sendFund(CustomerFund fund) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            log.info("sending fund to billing service : {}", fund);
            ResponseEntity<CustomerFund> result = this.restTemplate.postForEntity(billingUrl, fund, CustomerFund.class);
            return result.getBody();
        }
        catch (Exception ex){
            log.error(">>> An error occurred while sending request : {} ", ex.getMessage());
            throw new IllegalArgumentException("Internal server error. could not sync with billing service");
        }
    }
}
