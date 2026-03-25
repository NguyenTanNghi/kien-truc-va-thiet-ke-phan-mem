package com.example.servicebased.payment.application;

import com.example.servicebased.payment.application.dto.PaymentRequestDto;
import com.example.servicebased.payment.application.dto.PaymentResponseDto;
import com.example.servicebased.payment.domain.Payment;
import com.example.servicebased.payment.infrastructure.PaymentRepository;
import com.example.servicebased.shared.events.DomainEventPublisher;
import com.example.servicebased.shared.events.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentApplicationService {

    private final PaymentRepository paymentRepository;
    private final DomainEventPublisher eventPublisher;

    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto) {
        String paymentStatus = requestDto.getAmount() <= 1000 ? "SUCCESS" : "FAILED";

        Payment payment = Payment.builder()
                .orderId(requestDto.getOrderId())
                .status(paymentStatus)
                .build();

        Payment saved = paymentRepository.save(payment);
        eventPublisher.publish(new PaymentCompletedEvent(saved.getOrderId(), saved.getStatus()));

        return PaymentResponseDto.builder()
                .paymentId(saved.getId())
                .orderId(saved.getOrderId())
                .status(saved.getStatus())
                .build();
    }
}
