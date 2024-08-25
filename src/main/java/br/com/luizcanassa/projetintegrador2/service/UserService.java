package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.UserCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.UserDTO;
import br.com.luizcanassa.projetintegrador2.exception.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    void changeUserStatus(final Long id) throws UserNotFoundException, ChangeStatusUserException, ChangeStatusRootUserException;

    void createUser(final UserCreateDTO userCreateDTO) throws RoleNotFoundException;

    void deleteUser(final Long id) throws UserNotFoundException, DeleteRootUserException;
}
