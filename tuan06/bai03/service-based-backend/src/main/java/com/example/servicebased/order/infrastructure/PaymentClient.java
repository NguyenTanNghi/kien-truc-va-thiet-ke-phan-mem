package com.example.servicebased.order.infrastructure;

import com.example.servicebased.payment.application.dto.PaymentRequestDto;
import com.example.servicebased.payment.application.dto.PaymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PaymentClient {

    private final RestTemplate restTemplate;

    @Value("${payment.service.base-url}")
    private String paymentServiceBaseUrl;

    public PaymentResponseDto createPayment(Long orderId, Double amount) {
        PaymentRequestDto request = new PaymentRequestDto();
        request.setOrderId(orderId);
        request.setAmount(amount);

        return restTemplate.postForObject(
                paymentServiceBaseUrl + "/payments",
                request,
                PaymentResponseDto.class);
    }
}
