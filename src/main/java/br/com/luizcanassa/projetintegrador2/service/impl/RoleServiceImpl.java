package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.domain.dto.RoleDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.RoleEntity;
import br.com.luizcanassa.projetintegrador2.exception.RoleNotFoundException;
import br.com.luizcanassa.projetintegrador2.mapper.RoleMapper;
import br.com.luizcanassa.projetintegrador2.repository.RoleRepository;
import br.com.luizcanassa.projetintegrador2.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleServiceImpl(final RoleRepository roleRepository, final RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> findAll() {
        return roleRepository.findAllRoles().stream().map(roleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public RoleEntity findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Perfil não encontrado!"));
    }
}
