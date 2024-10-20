package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.BillingSummaryDTO;

import java.math.BigDecimal;

public interface OrderService {
    BigDecimal getTotalBillingOfDay();

    Integer getTotalOrdersOfMonth();

    BillingSummaryDTO getBillingLast8Months();
}
