package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.domain.dto.CustomUserDetails;
import br.com.luizcanassa.projetintegrador2.domain.dto.UserDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.UserEntity;
import br.com.luizcanassa.projetintegrador2.exception.ChangeStatusRootUserException;
import br.com.luizcanassa.projetintegrador2.exception.ChangeStatusUserException;
import br.com.luizcanassa.projetintegrador2.exception.UserNotFoundException;
import br.com.luizcanassa.projetintegrador2.mapper.UserDTOMapper;
import br.com.luizcanassa.projetintegrador2.repository.UserRepository;
import br.com.luizcanassa.projetintegrador2.service.UserService;
import br.com.luizcanassa.projetintegrador2.utils.AuthenticationUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
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
                .map(UserDTOMapper::userToUserDTO)
                .filter(userDTO -> {
                    if (AuthenticationUtils.isRoot()) {
                        return true;
                    } else return AuthenticationUtils.isRoot() || !userDTO.getIsRoot();
                })
                .collect(Collectors.toList());
    }

    @Override
    public void changeUserStatus(final Long id)
            throws UsernameNotFoundException, ChangeStatusUserException, ChangeStatusRootUserException
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

    private static boolean isRootUser(final UserEntity user) {
        return user.getRoles().stream().anyMatch(roleEntity -> roleEntity.getName().equals("ROLE_ROOT"));
    }

}
