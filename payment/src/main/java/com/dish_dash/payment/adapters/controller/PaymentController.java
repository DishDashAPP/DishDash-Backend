package com.dish_dash.payment.adapters.controller;

import com.dish_dash.payment.application.service.PaymentService;
import com.dish_dash.payment.domain.model.CurrencyUnit;
import com.dish_dash.payment.domain.model.Price;
import com.dish_dash.payment.domain.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public boolean pay(@RequestParam String transactionID) {
        return paymentService.pay(transactionID);
    }

    @PostMapping("/createOrderTransaction")
    public Transaction createOrderTransaction(@RequestParam String orderID, @RequestParam double amount) {
        Price price = new Price(amount, CurrencyUnit.TOMAN);
        return paymentService.createOrderTransaction(orderID, price);
    }
}
