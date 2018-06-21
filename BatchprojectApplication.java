package com.cardprocessor.cardprocessorbatchproject;

import com.cardprocessor.cardprocessorbatchproject.utils.StorageProperties;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableBatchProcessing
@EnableIntegration
@IntegrationComponentScan
@ComponentScan(basePackages = "com.cardprocessor")
@EnableConfigurationProperties(StorageProperties.class)
public class BatchprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchprojectApplication.class, args);
    }
}
