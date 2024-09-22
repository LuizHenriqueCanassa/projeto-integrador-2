package br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDeliveryDetailDTO {

    private Long id;

    private String document;

    private String address;

    private String details;

    private String orderItems;

    private String status;

    private Boolean paid;

    private BigDecimal totalAmount;

}
