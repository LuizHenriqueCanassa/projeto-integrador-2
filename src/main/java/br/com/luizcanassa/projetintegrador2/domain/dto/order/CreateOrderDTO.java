package br.com.luizcanassa.projetintegrador2.domain.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDTO {

    private String details;

    private List<OrderItemDTO> orderItems;

    @Data
    public static class OrderItemDTO {

        private Long productId;

        private Integer quantity;
    }
}
