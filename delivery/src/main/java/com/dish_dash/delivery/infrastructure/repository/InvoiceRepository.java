package com.dish_dash.delivery.infrastructure.repository;

import com.dish_dash.delivery.domain.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {}
