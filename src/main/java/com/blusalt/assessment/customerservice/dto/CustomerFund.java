package com.blusalt.assessment.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class CustomerFund {

    @NotBlank(message = "customer Id is required")
    long customerId;

    @NotBlank(message = "Amount can not be empty")
    String amount;
}
