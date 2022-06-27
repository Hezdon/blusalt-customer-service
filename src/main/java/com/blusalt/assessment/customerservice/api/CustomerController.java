package com.blusalt.assessment.customerservice.api;

import com.blusalt.assessment.customerservice.dto.CustomerFund;
import com.blusalt.assessment.customerservice.entity.Customer;
import com.blusalt.assessment.customerservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;


    @ResponseBody
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCustomer(@RequestBody @Valid Customer customer){
        log.info("new customer {}", customer);
        return new ResponseEntity<>(customerService.saveCustomer(customer).withMessage("Successful"), HttpStatus.OK);

    }

    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @PostMapping(value = "/fund")
    public ResponseEntity<?> fundCustomer(@RequestBody @Valid CustomerFund fund){

        log.info("new customer fund {}", fund);
        return new ResponseEntity<>(customerService.fundCustomer(fund), HttpStatus.OK);

    }
}
