package com.keepgoing.order.presentation.config.inner;

import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@Slf4j(topic = "AsyncConfig")
public class AsyncConfig implements AsyncConfigurer {

    /**
     * getAsyncExecutor를 오버라이딩해서 명확하게 정의해주지 않으면
     * Spring은 SimpleAsyncTaskExecutor를 사용
     * 이는 스레드 풀을 사용하지 않고 호출마다 새 스레드를 무제한 생성한다.
     * 그러므로 부하를 걸면 생성/삭제에 큰 비용이 발생하며 심각한 경우 OOM이 발생할 수 있다. (스레드 생성 제한이 없음)
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("event-async-");
        executor.initialize();
        return executor;
    }

    /**
     * 비동기 처리하게 되면 일반적으로 호출 스레드가 아니라 다른 스레드에 작업을 위임하기 때문에
     * 예외가 발생하더라도 예외가 전파되지 않아 호출 스레드가 알 수 없음
     * void 리턴인 경우는 @Async 메서드 호출자가 예외를 받을 방법이 없음
     * 그러므로 getAsyncUncaughtExceptionHandler를 재정의하여 예외에 대한 로그를 확실하게 작성한다.
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            log.error("비동기 작업 중 에외 발생 Method: {}, Exception: {}", method.getName(),
                ex.getMessage());
        };
    }
}
