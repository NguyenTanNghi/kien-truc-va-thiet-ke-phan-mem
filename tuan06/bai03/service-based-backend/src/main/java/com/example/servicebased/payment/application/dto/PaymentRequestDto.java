package com.example.servicebased.payment.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentRequestDto {

    @NotNull
    private Long orderId;

    @NotNull
    @Positive
    private Double amount;
}
