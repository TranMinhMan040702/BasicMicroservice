package com.mantm.notification;

import com.mantm.amqp.RabbitMQMessageProducer;
import com.mantm.notification.config.NotificationConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

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

    @Bean
    CommandLineRunner commandLineRunner(
            RabbitMQMessageProducer producer,
            NotificationConfig notificationConfig) {

        return args -> {
            producer.publish("iphone14",
                    notificationConfig.getInternalExchange(),
                    notificationConfig.getInternalNotificationRoutingKey());
        };
    }
}
