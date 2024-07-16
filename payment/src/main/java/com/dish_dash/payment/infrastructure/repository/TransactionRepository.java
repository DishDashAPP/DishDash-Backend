package com.dish_dash.payment.infrastructure.repository;


import com.dish_dash.payment.domain.model.Transaction;

public interface TransactionRepository {
    Transaction findByID(String transactionID);
    Transaction create(String orderID, Price price);
    boolean modify(Transaction transaction);
}

