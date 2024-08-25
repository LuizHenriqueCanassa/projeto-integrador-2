package br.com.luizcanassa.projetintegrador2.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDTO {

    private Long id;

    private String name;

    private Boolean active;

    private LocalDateTime createdAt;

}
