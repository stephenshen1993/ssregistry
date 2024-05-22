package com.stephenshen.ssregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({SSRegistryConfigProperties.class})
public class SSRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SSRegistryApplication.class, args);
    }

}
