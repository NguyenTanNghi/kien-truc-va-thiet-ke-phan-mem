package com.example.servicebased.payment.infrastructure;

import com.example.servicebased.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
