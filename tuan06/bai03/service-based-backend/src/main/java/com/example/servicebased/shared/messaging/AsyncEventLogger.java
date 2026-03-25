package com.example.servicebased.shared.messaging;

import com.example.servicebased.shared.events.OrderCreatedEvent;
import com.example.servicebased.shared.events.PaymentCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class AsyncEventLogger {

    @Async
    @TransactionalEventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("[Async-Event] OrderCreated published for orderId={}", event.getOrderId());
    }

    @Async
    @TransactionalEventListener
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        log.info("[Async-Event] PaymentCompleted published for orderId={}, status={}",
                event.getOrderId(), event.getStatus());
    }
}
