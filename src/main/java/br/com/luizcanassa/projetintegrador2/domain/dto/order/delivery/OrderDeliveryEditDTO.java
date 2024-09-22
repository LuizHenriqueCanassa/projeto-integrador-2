package br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery;

import lombok.Data;

@Data
public class OrderDeliveryEditDTO {
    private String status;

    private Boolean paid;
}
