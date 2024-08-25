package br.com.luizcanassa.projetintegrador2.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryCreateDTO {

    @NotNull(message = "O campo Nome é obrigatório")
    @Size(min = 10, max = 100, message = "O campo Nome deve possuir entra 10 e 100 caracteres")
    private String name;
}
