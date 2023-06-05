package com.mantm.customer.service;

import com.mantm.clients.fraud.FraudCheckResponse;
import com.mantm.clients.fraud.FraudClient;
import com.mantm.clients.notification.NotificationClient;
import com.mantm.clients.notification.NotificationRequest;
import com.mantm.customer.dto.CustomerRegistrationRequest;
import com.mantm.customer.entity.Customer;
import com.mantm.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
        .firstName(request.firstName()).lastName(request.lastName()).email(request.email())
        .build();

        // todo: check if fraudster
        customerRepository.save(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        // todo: make it async .i.e add to queue
        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to Website of ManTran ...", customer.getFirstName())
                )
        );
    }



}
