package br.com.luizcanassa.projetintegrador2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private BigDecimal price;

    private String category;

    private Boolean active;

}
