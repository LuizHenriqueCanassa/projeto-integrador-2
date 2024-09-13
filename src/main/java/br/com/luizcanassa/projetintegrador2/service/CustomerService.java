package br.com.luizcanassa.projetintegrador2.service;

import br.com.luizcanassa.projetintegrador2.domain.dto.customer.CustomerCreateDTO;
import br.com.luizcanassa.projetintegrador2.domain.dto.customer.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> findAll();

    void create(CustomerCreateDTO customerCreateDTO);
}
