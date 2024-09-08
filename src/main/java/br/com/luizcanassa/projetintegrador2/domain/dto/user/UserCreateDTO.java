package br.com.luizcanassa.projetintegrador2.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDTO {

    @NotNull(message = "O campo nome é obrigatório")
    @NotBlank(message = "O campo nome não pode ser vazio")
    @Size(max = 100, min = 10, message = "O campo nome deve ter entre 10 e 100 caracteres")
    private String name;

    @NotNull(message = "O campo nome de usuário é obrigatório")
    @NotBlank(message = "O campo nome de usuário não pode ser vazio")
    @Size(max = 30, min = 5, message = "O campo nome de usuário deve ter entre 5 e 30 caracteres")
    private String username;

    @NotNull(message = "O campo senha é obrigatório")
    @NotBlank(message = "O campo senha de usuário não pode ser vazio")
    @Size(max = 30, min = 6, message = "O campo senha deve ter entre 6 e 30 caracteres")
    private String password;

    @NotNull(message = "O perfil é obrigatório")
    private Long roleId;
}
