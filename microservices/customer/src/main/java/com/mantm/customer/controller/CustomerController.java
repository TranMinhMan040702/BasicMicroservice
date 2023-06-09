package com.mantm.customer.controller;

import com.mantm.customer.dto.CustomerRegistrationRequest;
import com.mantm.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public void Register (@RequestBody CustomerRegistrationRequest customer) {
        log.info("New customer registration {}", customer);
        customerService.registerCustomer(customer);
    }
}
