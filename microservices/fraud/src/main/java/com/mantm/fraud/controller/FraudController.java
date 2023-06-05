package com.mantm.fraud.controller;

import com.mantm.clients.fraud.FraudCheckResponse;
import com.mantm.fraud.service.FraudCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
@RequiredArgsConstructor
@Slf4j
public class FraudController {
    private final FraudCheckService fraudCheckService;

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId) {
        log.info("Fraud...??");
        return new FraudCheckResponse(fraudCheckService.isFraudulentCustomer(customerId));
    }
}
