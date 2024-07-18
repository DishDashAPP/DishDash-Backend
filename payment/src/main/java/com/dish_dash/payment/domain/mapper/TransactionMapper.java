package com.dish_dash.payment.domain.mapper;

import com.dishDash.common.dto.*;
import com.dish_dash.payment.domain.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {
  TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

  TransactionDto transactionToDto(Transaction transaction);
}
