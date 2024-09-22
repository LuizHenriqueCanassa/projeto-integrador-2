package br.com.luizcanassa.projetintegrador2.domain.dto.order.local;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLocalDTO {

    private Long id;

    private Long cardControl;

    private BigDecimal totalAmount;

    private Boolean paid;

    private String status;
}
