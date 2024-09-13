package br.com.luizcanassa.projetintegrador2.domain.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;

    private String name;

    private String document;

    private String mobilePhone;

}
