package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.UserCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.UserDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.UserEditDTO;
import br.com.luizcanassa.projetintegrador2.domain.entity.UserEntity;
import br.com.luizcanassa.projetintegrador2.exception.*;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    UserEditDTO findByIdToEdit(Long id) throws UserNotFoundException, EditRootUserException;

    void changeUserStatus(final Long id) throws UserNotFoundException, ChangeStatusUserException, ChangeStatusRootUserException;

    void createUser(final UserCreateDTO userCreateDTO) throws RoleNotFoundException;

    UserEntity editUser(final UserEditDTO userEditDTO) throws UserNotFoundException, EditRootUserException;

    void deleteUser(final Long id) throws UserNotFoundException, DeleteRootUserException;
}
