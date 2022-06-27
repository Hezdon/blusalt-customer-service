package com.blusalt.assessment.customerservice;

import com.blusalt.assessment.customerservice.dto.CustomerFund;
import com.blusalt.assessment.customerservice.entity.Customer;
import com.blusalt.assessment.customerservice.service.BillingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static com.blusalt.assessment.customerservice.util.Const.*;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = { TestWebConfig.class})
class CustomerServiceApplicationTests {

	private MockMvc mockMvc;
	@Value("${billing.customer.fund}")
	String billingUrl;

	@MockBean
	BillingService billingServiceMock;

	@Autowired
	private WebApplicationContext webApplicationContext;


	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

	}

	@Test
	@DisplayName(value = "Add New Customer Test")
	@SneakyThrows
	public void addCustomerTest_Successful(){

		Customer customer = new Customer().withName("test cut");
		MvcResult result = this.mockMvc.perform(post(getUri(NEW_CUSTOMER_SERVICE_URL))
				.characterEncoding("utf-8")
				.content(new ObjectMapper().writeValueAsString(customer))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Successful"))
				.andExpect(jsonPath("$.name").value(customer.getName()))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(status().isOk())
				.andReturn();

	}

	@Test
	@DisplayName(value = "Failed Add New Customer Test ")
	@SneakyThrows
	public void addCustomerTest_Failed(){

		Customer customer = new Customer().withName(" ");
		MvcResult result = this.mockMvc.perform(post(getUri(NEW_CUSTOMER_SERVICE_URL))
				.characterEncoding("utf-8")
				.content(new ObjectMapper().writeValueAsString(customer))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Customer name can not be empty"))
				.andExpect(status().isBadRequest())
				.andReturn();

	}

	@Test
	@DisplayName(value = "Add Fund to Customer Test")
	@SneakyThrows
	public void fundCustomerTest_Successful(){

		CustomerFund fund = new CustomerFund().withAmount("30000.00").withCustomerId(1);
		when(billingServiceMock.sendFund(any())).thenReturn(fund.withMessage("Pending"));

		MvcResult result = this.mockMvc.perform(post(getUri(ADD_FUND_SERVICE_URL))
				.characterEncoding("utf-8")
				.content(new ObjectMapper().writeValueAsString(fund))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Pending"))
				.andExpect(jsonPath("$.amount").value(fund.getAmount()))
				.andExpect(jsonPath("$.customerId").value(fund.getCustomerId()))
				.andExpect(status().isOk())
				.andReturn();

		verify(billingServiceMock, times(1)).sendFund(any());

	}

	@Test
	@DisplayName(value = "Failed Add Fund to Customer Test")
	@SneakyThrows
	public void fundCustomerTest_Failed(){

		CustomerFund fund = new CustomerFund().withAmount(" ").withCustomerId(1);
		when(billingServiceMock.sendFund(any())).thenReturn(fund.withMessage("Pending"));

		MvcResult result = this.mockMvc.perform(post(getUri(ADD_FUND_SERVICE_URL))
				.characterEncoding("utf-8")
				.content(new ObjectMapper().writeValueAsString(fund))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Amount can not be empty"))
				.andExpect(status().isBadRequest())
				.andReturn();

		verify(billingServiceMock, times(0)).sendFund(any());

	}


	private URI getUri(String serviceUrl) {
		URI uri = null;
		try {
			uri = new URI(serviceUrl);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}

}
