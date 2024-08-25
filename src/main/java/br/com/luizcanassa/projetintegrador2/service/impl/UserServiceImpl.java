package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.domain.dto.CustomUserDetails;
import br.com.luizcanassa.projetintegrador2.domain.dto.UserCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.UserDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.RoleEntity;
import br.com.luizcanassa.projetintegrador2.domain.entity.UserEntity;
import br.com.luizcanassa.projetintegrador2.exception.*;
import br.com.luizcanassa.projetintegrador2.mapper.UserMapper;
import br.com.luizcanassa.projetintegrador2.repository.RoleRepository;
import br.com.luizcanassa.projetintegrador2.repository.UserRepository;
import br.com.luizcanassa.projetintegrador2.service.RoleService;
import br.com.luizcanassa.projetintegrador2.service.UserService;
import br.com.luizcanassa.projetintegrador2.utils.AuthenticationUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(final UserRepository userRepository, final RoleService roleService, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final var user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        final Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName()))
                .collect(Collectors.toSet());

        return new CustomUserDetails(user.getName(), user.getUsername(), user.getPassword(), authorities, user.getActive());
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::userToUserDTO)
                .filter(userDTO -> {
                    if (AuthenticationUtils.isRoot()) {
                        return true;
                    } else return AuthenticationUtils.isRoot() || !userDTO.getIsRoot();
                })
                .collect(Collectors.toList());
    }

    @Override
    public void changeUserStatus(final Long id)
            throws UserNotFoundException, ChangeStatusUserException, ChangeStatusRootUserException
    {
        final var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado!"));

        if (isRootUser(user)) {
            throw new ChangeStatusRootUserException("Não é possível alterar o status de um usuário ROOT");
        }

        if (user.getUsername().equals(AuthenticationUtils.getUsername())) {
            throw new ChangeStatusUserException("Não é possível alterar o status do seu proprio usuário");
        }

        user.setActive(!user.getActive());

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void createUser(final UserCreateDTO userCreateDTO) throws RoleNotFoundException {
        final var userToCreate = UserMapper.userCreateDTOToUserEntity(userCreateDTO, passwordEncoder.encode(userCreateDTO.getPassword()));
        userToCreate.setRoles(
                Collections.singleton(
                        roleService.findById(userCreateDTO.getRoleId())
                )
        );

        userRepository.saveAndFlush(userToCreate);
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException, DeleteRootUserException {
        final var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado!"));

        if (isRootUser(user)) {
            throw new DeleteRootUserException("Não é possível deleter um usuário ROOT!");
        }

        if (user.getUsername().equals(AuthenticationUtils.getUsername())) {
            throw new DeleteUserException("Não é possível deletar o seu proprio usuário");
        }

        userRepository.delete(user);
    }

    private static boolean isRootUser(final UserEntity user) {
        return user.getRoles().stream().anyMatch(roleEntity -> roleEntity.getName().equals("ROLE_ROOT"));
    }

}
