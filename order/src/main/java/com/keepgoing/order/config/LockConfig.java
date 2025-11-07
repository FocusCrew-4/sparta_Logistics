package com.keepgoing.order.config;

import com.keepgoing.order.application.lock.CaffeineStepLock;
import com.keepgoing.order.application.lock.StepLock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockConfig {

    @Bean
    public StepLock stepLock(
        @Value("${lock.local.ttl-seconds}") long ttlSeconds,
        @Value("${lock.local.max-size}") long maxSize
    ) {
        return new CaffeineStepLock(ttlSeconds, maxSize);
    }

}
