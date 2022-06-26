package com.blusalt.assessment.customerservice.api;

import com.blusalt.assessment.customerservice.dto.CustomerFund;
import com.blusalt.assessment.customerservice.entity.Customer;
import com.blusalt.assessment.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;


    @PostMapping(value = "/add")
    public ResponseEntity<?> addCustomer(@RequestBody @Valid Customer customer){

        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.OK);

    }

    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @PostMapping(value = "/fund")
    public void fundCustomer(@RequestBody @Valid CustomerFund fund){

        customerService.fundCustomer(fund);

    }
}