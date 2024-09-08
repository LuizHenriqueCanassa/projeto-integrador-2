package br.com.luizcanassa.projetintegrador2.domain.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryEditDTO {

    @NotNull(message = "O campo ID é obrigatório")
    private Long id;

    @NotNull(message = "O campo Nome é obrigatório")
    @Size(min = 5, max = 100, message = "O campo Nome precisa ter entre 5 e 100 caracteres")
    private String name;

}
