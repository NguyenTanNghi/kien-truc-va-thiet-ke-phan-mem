package com.example.servicebased.payment.application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PaymentResponseDto {
    Long paymentId;
    Long orderId;
    String status;
}
