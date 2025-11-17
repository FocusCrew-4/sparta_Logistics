package com.sparta.hub.routes.application.event;

import com.sparta.hub.routes.domain.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publish(DomainEvent event) {
        log.debug("이벤트 발행 - {}", event.getClass().getSimpleName());
        publisher.publishEvent(event);
    }
}