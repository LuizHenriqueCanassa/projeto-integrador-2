package br.com.luizcanassa.projetintegrador2.mapper;

import br.com.luizcanassa.projetintegrador2.domain.dto.RoleDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.RoleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class RoleMapper {

    public static RoleDTO toDto(final RoleEntity role) {
        return new RoleDTO(role.getId(), role.getDescription());
    }
}
