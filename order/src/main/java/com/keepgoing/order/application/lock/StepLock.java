package com.keepgoing.order.application.lock;

import java.time.Duration;

public interface StepLock {

    boolean tryAcquire(String key, Duration ttl);

    void release(String key);
}
