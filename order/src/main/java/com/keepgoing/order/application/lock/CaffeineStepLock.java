package com.keepgoing.order.application.lock;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;

public class CaffeineStepLock implements StepLock{

    private final Cache<String, Boolean> cache;

    public CaffeineStepLock(long ttlSeconds, long maxSize) {
        this.cache = Caffeine.newBuilder()
            .expireAfterWrite(java.time.Duration.ofSeconds(ttlSeconds)) // 기본 TTL
            .maximumSize(maxSize)
            .build();
    }

    @Override
    public boolean tryAcquire(String key, Duration ttl) {
        // 락이 없으면 true, 있으면 false
        boolean acquired = !cache.asMap().containsKey(key);

        if (acquired) {
            cache.put(key, Boolean.TRUE);
        }

        return acquired;
    }

    @Override
    public void release(String key) {
        cache.invalidate(key);
    }
}
