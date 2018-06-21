package com.cardprocessor.cardprocessorbatchproject;

import com.cardprocessor.cardprocessorbatchproject.notification.WebSocketNotificationClient;
import com.cardprocessor.cardprocessorbatchproject.utils.StorageProperties;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication
@EnableBatchProcessing
@EnableIntegration
@IntegrationComponentScan
@ComponentScan(basePackages = "com.cardprocessor")
@EnableConfigurationProperties(StorageProperties.class)
@EnableScheduling
public class BatchprojectApplication {


    @Autowired
    WebSocketNotificationClient webSocketNotificationClient;

    public static void main(String[] args) {
        SpringApplication.run(BatchprojectApplication.class, args);
    }

    @Scheduled(fixedRate = 1000)
    public void reminder() {

        webSocketNotificationClient.sendNotificationToUser("superuser", "uploaded File processed successfully");

    }
}
