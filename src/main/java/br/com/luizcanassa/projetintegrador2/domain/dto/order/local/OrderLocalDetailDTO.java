package br.com.luizcanassa.projetintegrador2.domain.dto.order.local;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLocalDetailDTO {

    private Long id;

    private Integer cardControl;

    private String details;

    private String orderItems;

    private String status;

    private Boolean paid;

    private BigDecimal totalAmount;

}
