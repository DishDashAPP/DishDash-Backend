package com.dish_dash.delivery.domain.mapper;

import com.dishDash.common.dto.InvoiceDto;
import com.dish_dash.delivery.domain.model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeliveryMapper {
    DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);

    InvoiceDto invoiceToDto(Invoice invoice);
}
