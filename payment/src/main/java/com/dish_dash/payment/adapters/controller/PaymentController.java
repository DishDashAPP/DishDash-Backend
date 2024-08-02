package com.dish_dash.payment.adapters.controller;

import com.dishDash.common.Price;
import com.dishDash.common.dto.TransactionDto;
import com.dishDash.common.enums.CurrencyUnit;
import com.dishDash.common.feign.payment.PaymentApi;
import com.dish_dash.payment.application.service.PaymentService;
import com.dish_dash.payment.domain.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {
  private final PaymentService paymentService;

  @Override
  public boolean pay(String transactionID) {
    return paymentService.pay(transactionID);
  }

  @Override
  public TransactionDto createOrderTransaction(Long shoppingCartId, double amount) {
    Price price = Price.builder().amount(amount).unit(CurrencyUnit.TOMAN).build();
    return TransactionMapper.INSTANCE.transactionToDto(
        paymentService.createOrderTransaction(shoppingCartId, price));
  }
}
