package com.dishDash.common;

import com.dishDash.common.enums.CurrencyUnit;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Price {
  private double amount;
  private CurrencyUnit unit;
}
