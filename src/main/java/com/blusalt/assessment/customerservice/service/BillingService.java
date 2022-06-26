package com.blusalt.assessment.customerservice.service;

import com.blusalt.assessment.customerservice.dto.CustomerFund;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface BillingService {

    public HttpStatus sendFund(CustomerFund fund);
}
