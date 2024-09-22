package br.com.luizcanassa.projetintegrador2.domain.dto.order.local;

import br.com.luizcanassa.projetintegrador2.domain.dto.order.CreateOrderDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreateOrderLocalDTO extends CreateOrderDTO {

    @NotNull(message = "O campo Número da Comanda é obrigatório")
    private Integer cardControl;

}
