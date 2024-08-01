package com.dish_dash.order;

import com.dishDash.common.dto.RateDto;
import com.dish_dash.order.application.service.RateService;
import com.dish_dash.order.domain.model.Rate;
import com.dish_dash.order.domain.repository.RateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RateServiceIntegrationTest {

  @Autowired private RateService rateService;

  @Autowired private RateRepository rateRepository;

  private Rate rate;

  @BeforeEach
  void setUp() {
    rateRepository.deleteAll();
    rateRepository.flush();

    rate = Rate.builder().customerId(1L).orderId(1L).point(5).deliveryPersonID("D123").build();
  }

  @Test
  void setOrderRate_ShouldReturnTrue_WhenRateSavedSuccessfully() {
    boolean result = rateService.setOrderRate(1L, 1L, 5);

    assertTrue(result, "Order rate should be set successfully");

    Rate savedRate = rateRepository.findByCustomerIdAndOrderId(1L, 1L);
    assertNotNull(savedRate, "Saved rate should not be null");
    assertEquals(5, savedRate.getPoint(), "Rate point should be 5");
  }

  @Test
  void setDeliveryRate_ShouldReturnTrue_WhenRateSavedSuccessfully() {
    boolean result = rateService.setDeliveryRate(1L, 1L, 4);

    assertTrue(result, "Delivery rate should be set successfully");

    Rate savedRate = rateRepository.findByCustomerIdAndOrderId(1L, 1L);
    assertNotNull(savedRate, "Saved rate should not be null");
    assertEquals(4, savedRate.getPoint(), "Rate point should be 4");
  }

  @Test
  void getDeliveryRate_ShouldReturnRateDto_WhenDeliveryPersonIdExists() {
    rateRepository.saveAndFlush(rate);

    RateDto result = rateService.getDeliveryRate("D123");

    assertNotNull(result, "Retrieved RateDto should not be null");
    assertEquals(rate.getId(), result.getId(), "Retrieved rate ID should match expected");
  }

  @Test
  void getDeliveryRate_ShouldReturnNull_WhenDeliveryPersonIdDoesNotExist() {
    RateDto result = rateService.getDeliveryRate("UNKNOWN_ID");

    assertNull(result, "Retrieved RateDto should be null for unknown delivery person ID");
  }
}
