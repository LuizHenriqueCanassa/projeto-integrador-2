package br.com.luizcanassa.projetintegrador2.domain.dto.product;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductEditDTO {

    private Long id;

    @NotNull(message = "O campo Nome é obrigatório")
    @Size(min = 10, max = 100, message = "O campo Nome deve possuir entra 10 e 100 caracteres")
    private String name;

    @NotNull(message = "O campo Descrição é obrigatório")
    @Size(min = 10, message = "O campo Descrição deve possuir ao menos 10 caracteres")
    private String description;

    @NotNull(message = "O campo Preço é obrigatório")
    @Digits(integer = 10, fraction = 2, message = "O Preço foi informado incorretamente (Ex: 10.30 ou 150.00)")
    private BigDecimal price;

    @NotNull(message = "O campo Categoria é obrigatório")
    private Long categoryId;
}
