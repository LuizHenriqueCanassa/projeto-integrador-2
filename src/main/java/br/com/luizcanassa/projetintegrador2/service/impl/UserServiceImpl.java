package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.domain.dto.CustomUserDetails;
import br.com.luizcanassa.projetintegrador2.domain.dto.UserDTO;
import br.com.luizcanassa.projetintegrador2.repository.UserRepository;
import br.com.luizcanassa.projetintegrador2.service.UserService;
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
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getUsername(),
                        user.getActive(),
                        user.getCreatedAt()
                        )
                ).collect(Collectors.toList());
    }
}
