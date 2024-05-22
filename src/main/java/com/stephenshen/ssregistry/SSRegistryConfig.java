package com.stephenshen.ssregistry;

import com.stephenshen.ssregistry.cluster.Cluster;
import com.stephenshen.ssregistry.health.HealthChecker;
import com.stephenshen.ssregistry.health.SSHealthChecker;
import com.stephenshen.ssregistry.service.RegistryService;
import com.stephenshen.ssregistry.service.SSRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for all beans.
 *
 * @author stephenshen
 * @date 2024/5/14 07:48:56
 */
@Configuration
public class SSRegistryConfig {

    @Bean
    public RegistryService registryService() {
        return new SSRegistryService();
    }

//    @Bean(initMethod = "start", destroyMethod = "stop")
//    public HealthChecker healthChecker(@Autowired RegistryService registryService) {
//        return new SSHealthChecker(registryService);
//    }

    @Bean(initMethod = "init")
    public Cluster cluster(@Autowired SSRegistryConfigProperties registryConfigProperties) {
        return new Cluster(registryConfigProperties);
    }
}
