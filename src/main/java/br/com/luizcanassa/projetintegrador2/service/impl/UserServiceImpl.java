package br.com.luizcanassa.projetintegrador2.service.impl;

import br.com.luizcanassa.projetintegrador2.repository.UserRepository;
import br.com.luizcanassa.projetintegrador2.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
