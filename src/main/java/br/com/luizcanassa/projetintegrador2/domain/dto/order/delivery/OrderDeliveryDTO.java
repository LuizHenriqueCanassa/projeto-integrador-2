package br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDeliveryDTO {

    private Long id;

    private String document;

    private BigDecimal totalAmount;

    private String status;
}
