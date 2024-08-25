package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.UserCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.UserDTO;
import br.com.luizcanassa.projetintegrador2.exception.ChangeStatusRootUserException;
import br.com.luizcanassa.projetintegrador2.exception.ChangeStatusUserException;
import br.com.luizcanassa.projetintegrador2.exception.RoleNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    void changeUserStatus(final Long id) throws UsernameNotFoundException, ChangeStatusUserException, ChangeStatusRootUserException;

    void createUser(final UserCreateDTO userCreateDTO) throws RoleNotFoundException;
}
