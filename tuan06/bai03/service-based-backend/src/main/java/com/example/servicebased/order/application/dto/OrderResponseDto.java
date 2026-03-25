package com.example.servicebased.order.application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderResponseDto {
    Long id;
    Long userId;
    Double amount;
    String status;
}
