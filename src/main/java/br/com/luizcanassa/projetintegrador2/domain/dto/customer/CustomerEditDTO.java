package br.com.luizcanassa.projetintegrador2.domain.dto.customer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerEditDTO {

    private Long id;

    @NotNull
    @NotBlank(message = "O campo Nome é obrigatório")
    private String name;

    @NotNull
    @NotBlank(message = "O campo Documento é obrigatório")
    private String document;

    @NotNull
    @NotBlank(message = "O campo Telefone é obrigatório")
    private String mobilePhone;

    @Valid
    private AddressEditDTO address;

    @Data
    public static class AddressEditDTO {
        private Long id;

        @NotNull
        @NotBlank(message = "O campo Endereço é obrigatório")
        private String streetName;

        @NotNull(message = "O campo Número é obrigatório")
        private Integer number;

        @NotNull
        @NotBlank(message = "O campo Cidade é obrigatório")
        private String city;

        @NotNull
        @NotBlank(message = "O campo Bairro é obrigatório")
        private String district;
    }
}
