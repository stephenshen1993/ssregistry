package com.stephenshen.ssregistry.health;

import com.stephenshen.ssregistry.model.InstanceMeta;
import com.stephenshen.ssregistry.service.RegistryService;
import com.stephenshen.ssregistry.service.SSRegistryService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author stephenshen
 * @date 2024/5/15 07:12:14
 */
@Slf4j
public class SSHealthChecker implements HealthChecker {

    RegistryService registryService;

    final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    long timeout = 20 * 1000;

    public SSHealthChecker(RegistryService registryService) {
        this.registryService = registryService;
    }

    @Override
    public void start() {
        executor.scheduleWithFixedDelay(
                () -> {
                    log.info("Health checker is running...");
                    long now = System.currentTimeMillis();
                    SSRegistryService.TIMESTAMPS.keySet().forEach(serviceAndInst -> {
                        Long timestamp = SSRegistryService.TIMESTAMPS.get(serviceAndInst);
                        if (now - timestamp > timeout) {
                            log.info(" ===> Health checker: {} is down", serviceAndInst);
                            int index = serviceAndInst.indexOf("@");
                            String service = serviceAndInst.substring(0, index);
                            String url = serviceAndInst.substring(index + 1);
                            InstanceMeta meta = InstanceMeta.from(url);
                            registryService.unregister(service, meta);
                            SSRegistryService.TIMESTAMPS.remove(serviceAndInst);
                        }
                    });
                },
                10, 10, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        executor.shutdown();
    }
}
