package com.dishDash.common.feign.payment;

import com.dishDash.common.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service")
public interface PaymentApi {
  @PostMapping("/pay")
  boolean pay(@RequestParam String transactionID);

  @PostMapping("/createOrderTransaction")
  TransactionDto createOrderTransaction(@RequestParam Long orderId, @RequestParam double amount);
}
