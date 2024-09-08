package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.role.RoleDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.RoleEntity;

import java.util.List;

public interface RoleService {
    List<RoleDTO> findAll();

    RoleEntity findById(final Long id);
}
