package com.example.servicebased.order.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    @Positive
    private Double amount;
}
