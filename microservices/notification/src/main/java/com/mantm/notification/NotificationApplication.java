package com.mantm.notification;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication (scanBasePackages = {
        "com.mantm.amqp",
        "com.mantm.notification"
})
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.mantm.clients")
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }
}
