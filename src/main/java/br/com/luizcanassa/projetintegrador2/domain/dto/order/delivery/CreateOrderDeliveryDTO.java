package br.com.luizcanassa.projetintegrador2.domain.dto.order.delivery;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.CreateOrderDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreateOrderDeliveryDTO extends CreateOrderDTO {

    @NotNull(message = "O campo Documento é obrigatório")
    private String document;

}
