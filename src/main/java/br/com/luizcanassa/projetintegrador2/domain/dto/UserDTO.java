package br.com.luizcanassa.projetintegrador2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    private String name;

    private String username;

    private Boolean active;

    private Boolean isRoot;

    private LocalDateTime createdAt;
}
