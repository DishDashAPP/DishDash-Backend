package com.dish_dash.payment.application.service;

import com.dish_dash.payment.domain.model.Payment;
import com.dish_dash.payment.domain.model.Transaction;
import com.dish_dash.payment.infrastructure.repository.PaymentRepository;
import com.dish_dash.payment.infrastructure.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public boolean pay(String transactionID) {
        Transaction transaction = transactionRepository.findByID(transactionID);
        if (transaction != null) {
            transaction.setStatus(TransactionStatus.PAID);
            transactionRepository.modify(transaction);
            Payment payment = new Payment(transactionID);
            return paymentRepository.create(payment);
        }
        return false;
    }

    public Transaction createOrderTransaction(String orderID, Price price) {
        return transactionRepository.create(orderID, price);
    }
}
