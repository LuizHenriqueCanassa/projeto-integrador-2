package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();
}
