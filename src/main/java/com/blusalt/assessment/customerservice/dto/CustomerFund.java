package com.blusalt.assessment.customerservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@JsonIgnoreProperties
public class CustomerFund implements Serializable {

    long customerId;

    @NotBlank(message = "Amount can not be empty")
    String amount;

    transient
    String message;
}
