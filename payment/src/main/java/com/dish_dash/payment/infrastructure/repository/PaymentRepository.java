package com.dish_dash.payment.infrastructure.repository;

import com.dish_dash.payment.domain.model.Payment;

public interface PaymentRepository {
    Payment findByID(String traceID);
    boolean create(Payment payment);
    boolean modify(Payment payment);
}

