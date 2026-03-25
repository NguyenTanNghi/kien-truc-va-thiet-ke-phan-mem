package com.example.servicebased.order.application;

import com.example.servicebased.order.application.dto.OrderRequestDto;
import com.example.servicebased.order.application.dto.OrderResponseDto;
import com.example.servicebased.order.domain.Order;
import com.example.servicebased.order.infrastructure.OrderRepository;
import com.example.servicebased.order.infrastructure.PaymentClient;
import com.example.servicebased.payment.application.dto.PaymentResponseDto;
import com.example.servicebased.shared.events.DomainEventPublisher;
import com.example.servicebased.shared.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderApplicationService {

    private final OrderRepository orderRepository;
    private final PaymentClient paymentClient;
    private final DomainEventPublisher eventPublisher;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        Order order = Order.builder()
                .userId(requestDto.getUserId())
                .amount(requestDto.getAmount())
                .status("CREATED")
                .build();

        Order saved = orderRepository.save(order);
        eventPublisher.publish(new OrderCreatedEvent(saved.getId()));

        PaymentResponseDto paymentResponse = paymentClient.createPayment(saved.getId(), saved.getAmount());
        String updatedStatus = paymentResponse != null && "SUCCESS".equalsIgnoreCase(paymentResponse.getStatus())
                ? "PAID"
                : "PAYMENT_FAILED";

        saved.setStatus(updatedStatus);
        Order updated = orderRepository.save(saved);

        return toResponse(updated);
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id=" + id));
        return toResponse(order);
    }

    private OrderResponseDto toResponse(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .amount(order.getAmount())
                .status(order.getStatus())
                .build();
    }
}
