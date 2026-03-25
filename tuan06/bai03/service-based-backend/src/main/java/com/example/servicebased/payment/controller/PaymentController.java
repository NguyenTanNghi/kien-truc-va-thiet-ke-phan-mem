package com.example.servicebased.payment.controller;

import com.example.servicebased.payment.application.PaymentApplicationService;
import com.example.servicebased.payment.application.dto.PaymentRequestDto;
import com.example.servicebased.payment.application.dto.PaymentResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentApplicationService paymentApplicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponseDto createPayment(@Valid @RequestBody PaymentRequestDto requestDto) {
        return paymentApplicationService.createPayment(requestDto);
    }
}
