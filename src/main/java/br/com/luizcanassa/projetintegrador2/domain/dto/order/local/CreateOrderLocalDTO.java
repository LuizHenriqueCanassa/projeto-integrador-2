package br.com.luizcanassa.projetintegrador2.domain.dto.order.local;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderLocalDTO {

    @NotNull(message = "O campo Número da Comanda é obrigatório")
    private Integer cardControl;

    private List<OrderItemDTO> orderItems;

    @Data
    public static class OrderItemDTO {

        private Long productId;

        private Integer quantity;
    }
}
