package com.blusalt.assessment.customerservice.service;

import com.blusalt.assessment.customerservice.dto.CustomerFund;

public interface BillingService {

    CustomerFund sendFund(CustomerFund fund);
}
