package com.dish_dash.gateway.adapters;

import com.dishDash.common.dto.TransactionDto;
import com.dishDash.common.feign.payment.PaymentApi;
import com.dish_dash.gateway.annotation.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
  private final PaymentApi paymentApi;

  @PostMapping("/pay")
  @Authentication
  boolean pay(@RequestParam String transactionID) {
    return paymentApi.pay(transactionID);
  }

  @PostMapping("/createOrderTransaction")
  @Authentication
  TransactionDto createOrderTransaction(@RequestParam long orderId, @RequestParam double amount) {
    return paymentApi.createOrderTransaction(orderId, amount);
  }
}
