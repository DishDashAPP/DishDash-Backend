package com.dish_dash.payment.infrastructure.repository;

public interface PaymentRepository {
    Payment findByID(String traceID);
    boolean create(Payment payment);
    boolean modify(Payment payment);
}

