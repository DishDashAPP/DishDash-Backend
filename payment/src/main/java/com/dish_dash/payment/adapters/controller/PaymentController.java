package com.dish_dash.payment.adapters.controller;

import com.dishDash.common.Price;
import com.dishDash.common.enums.CurrencyUnit;
import com.dish_dash.payment.application.service.PaymentService;
import com.dish_dash.payment.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
  private final PaymentService paymentService;

  @PostMapping("/pay")
  public boolean pay(@RequestParam String transactionID) {
    return paymentService.pay(transactionID);
  }

  @PostMapping("/createOrderTransaction")
  public Transaction createOrderTransaction(
      @RequestParam Long orderId, @RequestParam double amount) {
    Price price = Price.builder().amount(amount).unit(CurrencyUnit.TOMAN).build();
    return paymentService.createOrderTransaction(orderId, price);
  }
}
